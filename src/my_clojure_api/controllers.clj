(ns my-clojure-api.controllers
  (:require [clj-http.client :as client]
            [my-clojure-api.infraConfigs :refer [get-supabase-token get-api-key get-news-api-token]]))

(defn buscar-usuario-por-email-e-senha
  "Busca um usuário no Supabase pelo email e senha"
  [email senha]
  (println "Token usado para autenticação:" (get-supabase-token))
  (let [url (str "https://xfmwwaqypjiqalpgqamr.supabase.co/rest/v1/usuarios"
                 "?email=eq." email
                 "&senha=eq." senha)
        headers {"Authorization" (str "Bearer " (get-supabase-token))
                                 "apikey" (get-api-key)
                  "Content-Type" "application/json"}]
    (try
      (println "Entrando no try do request")
      (let [response (client/get url {:headers headers :as :json})]
        (println "Resposta do Supabase:" response)
        (if (or (nil? (:body response)) (empty? (:body response)))
          {:status 404 :message "Usuário não encontrado"}
          {:status 200
           :message "Usuário autenticado com sucesso"
           :data (first (:body response))}))
      (catch Exception e
        (println "Erro ao buscar usuário:" (.getMessage e))
        {:status 500 :message "Erro ao buscar usuário" :error (.getMessage e)}))))


(defn buscar-todos-usuarios
  "Busca todos os usuários no Supabase"
  []
  (let [url "https://xfmwwaqypjiqalpgqamr.supabase.co/rest/v1/usuarios"
        headers {"Authorization" (str "Bearer " (get-supabase-token))
                 "apikey" (get-supabase-token)
                 "Content-Type" "application/json"}]
    (try
      (println "Entrando no try do request para buscar todos os usuários")
      ;; Requisição GET para buscar todos os usuários
      (let [response (client/get url {:headers headers :as :json})]
        (println "Resposta do Supabase:" response)
        (if (or (nil? (:body response)) (empty? (:body response)))
          {:status 404 :message "Nenhum usuário encontrado"}
          {:status 200
           :message "Usuários encontrados com sucesso"
           :data (:body response)})) ;; Retorna todos os usuários encontrados
      (catch Exception e
        (println "Erro ao buscar todos os usuários:" (.getMessage e))
        {:status 500 :message "Erro ao buscar todos os usuários" :error (.getMessage e)}))))

(defn salvar-noticias
  "Faz a requisição à API externa e salva as notícias no banco Supabase."
  []
  (println "entro no metodo salve notiacs")
  (println "Token da API de notícias:" (get-news-api-token))
  (try
    ;; Requisição para a API de notícias (exemplo: New York Times)
    (let [api-url "https://api.nytimes.com/svc/news/v3/content/all/all.json"
          api-key (get-news-api-token) 
          response (client/get api-url {:query-params {"api-key" api-key}
                                        :as :json})
          noticias (:results (:body response))]
      (println "bateu na outr api")
      (println "result" response)
      ;; Salva cada notícia no banco de dados
      (doseq [noticia noticias]
        (let [url "https://xfmwwaqypjiqalpgqamr.supabase.co/rest/v1/noticias"
              headers {"Authorization" (str "Bearer " (get-supabase-token))
                       "apikey" (get-supabase-token)
                       "Content-Type" "application/json"}
              body {:title (:title noticia)
                    :abstract (:abstract noticia)
                    :url (:url noticia)
                    :published_date (:published_date noticia)
                    :source (:source noticia)
                    :likes 0}]
          (try
            ;; Faz o POST no banco de dados
            (client/post url {:headers headers :form-params body :as :json})
            (catch Exception e
              (println "Erro ao salvar notícia no banco:" (.getMessage e))))))
      ;; Retorno de sucesso
      {:status 200 :message "Notícias salvas com sucesso!"})
    (catch Exception e
      ;; Tratamento de erro
      (println "Erro ao buscar ou salvar notícias:" (.getMessage e))
      {:status 500 :message "Erro ao salvar notícias" :error (.getMessage e)})))

(defn curtir-noticia
  "Incrementa o contador de curtidas de uma notícia no banco."
  [news-id]
  (try
    (let [url (str "https://xfmwwaqypjiqalpgqamr.supabase.co/rest/v1/noticias?id=eq." news-id)
          headers {"Authorization" (str "Bearer " (get-supabase-token))
                   "apikey" (get-supabase-token)
                   "Content-Type" "application/json"}
          ;; Incrementa o contador de curtidas
          body {:likes [:increment 1]}]
      (println "URL da requisição:" url)
      (println "Cabeçalhos enviados:" headers)
      (let [response (client/patch url {:headers headers :form-params body :as :json})]
        (println "Resposta do Supabase:" response)
        (if (= 204 (:status response))
          {:status 200 :message "Notícia curtida com sucesso!"}
          {:status 400 :message "Erro ao curtir notícia"})))
    (catch Exception e
      (println "Erro ao curtir notícia:" (.getMessage e))
      {:status 500 :message "Erro ao curtir notícia" :error (.getMessage e)})))
