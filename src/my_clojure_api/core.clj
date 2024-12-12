(ns my-clojure-api.core
  (:gen-class :require [org.httpkit.server :refer [run-server]]
              [my-clojure-api.controllersc :as controllers]))

(def supabase-config
  {:url "https://xfmwwaqypjiqalpgqamr.supabase.co"
   :api-key "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InhmbXd3YXF5cGppcWFscGdxYW1yIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzM3OTk2NzksImV4cCI6MjA0OTM3NTY3OX0.Qx-Q7XT0X8F_BHWmL5p7Gb-BpNbvUxxDtjG8sgR0HPE"})

(def news-config
  {:url "https://api.nytimes.com/svc/news/v3/content/all/all.json"
   :api-key "1TH9vj0F5Wy6fgJRKAWcVnGbKAIsSCLT"})


(defn login-handler
  "Endpoint para autenticação do usuário"
  [request]
  (let [params (:body request) ;; Obtém o corpo da requisição (JSON)
        email (:email params)
        senha (:senha params)]
    (if (and email senha)
      (controllers/buscar-usuario-por-email-e-senha email senha)
      {:status 400 :message "Email e senha são obrigatórios"})))

(defn -main
  "Inicializa o servidor"
  [& args]
  (run-server
   (fn [request]
     (case (:uri request)
       "/login" (login-handler request)
       {:status 404 :body "Endpoint não encontrado"}))
   {:port 3000})
  (println "Servidor iniciado na porta 3000"))