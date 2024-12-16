(ns my-clojure-api.controllers
  (:require [clj-http.client :as client]
            [my-clojure-api.infraConfigs :refer [get-supabase-token get-api-key]]))

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
