(ns my-clojure-api.core
  (:gen-class)
  (:require [org.httpkit.server :refer [run-server]]
            [cheshire.core :as json]
            [my-clojure-api.controllers :as controllers]))

(defn login-handler
  "Endpoint para autenticação do usuário"
  [request]
  (try
    (let [body (slurp (:body request)) ;; Lê o corpo como string
          params (json/parse-string body true) ;; Decodifica JSON para mapa
          email (:email params)
          senha (:senha params)]
      (if (and email senha)
        (let [response (controllers/buscar-usuario-por-email-e-senha email senha)]
          ;; Garante que o retorno esteja no formato esperado
          {:status (:status response)
           :body (json/generate-string response)}) ;; Serializa o JSON antes de enviar
        {:status 400 :body "Email e senha são obrigatórios"}))
    (catch Exception e
      {:status 500 :body (str "Erro ao processar a solicitação: " (.getMessage e))})))

(defn get-all-user-handler
  "Endpoint lista todos usuarios"
  [request]
  (try
    ;; Supondo que você não precisa do corpo aqui
    (let [response (controllers/buscar-todos-usuarios)]
      ;; Garante que o retorno esteja no formato esperado
      {:status (:status response)
       :body (json/generate-string response)}) ;; Serializa o JSON antes de enviar
    (catch Exception e
      {:status 500 :body (str "Erro ao processar a solicitação: " (.getMessage e))})))

(defn save-news
  "Endpoint para salvar notícias"
  [request]
  (try
    (let [response (controllers/salvar-noticias)]
      ;; Retorna o status da operação
      {:status (:status response)
       :body (json/generate-string response)}) ;; Serializa a resposta para JSON
    (catch Exception e
      {:status 500 :body (str "Erro ao processar a solicitação: " (.getMessage e))})))

(defn -main
  "Inicializa o servidor"
  [& args]
  (run-server
   (fn [request]
     (let [uri (:uri request)]
       (println "Recebendo requisição para URI:" uri) ;; Log para depuração
       (cond
         (= uri "/login")
         (try
           (login-handler request)
           (catch Exception e
             {:status 500
              :body (str "Erro interno no servidor: " (.getMessage e))}))
        
         (= uri "/news/save")
         (try
           (save-news request)
           (catch Exception e
             {:status 500
              :body (str "Erro interno no servidor: " (.getMessage e))}))

         (= uri "/getAllUser")
         (try
           (get-all-user-handler request)
           (catch Exception e
             {:status 500
              :body (str "Erro interno no servidor: " (.getMessage e))}))

         :else
         {:status 404 :body "Endpoint não encontrado"})))
   {:port 3000})
  (println "Servidor iniciado na porta 3000!"))
