(ns my-clojure-api.controllers
  (:require [clj-http.client :as client]))

(defn buscar-usuario-por-email-e-senha
  "Busca um usuário no Supabase pelo email e senha"
  [email senha]
  (let [url (str "https://xfmwwaqypjiqalpgqamr.supabase.co/rest/v1/usuarios"
                 "?email=eq." email
                 "&senha=eq." senha)
        headers {"Authorization" "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InhmbXd3YXF5cGppcWFscGdxYW1yIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzM3OTk2NzksImV4cCI6MjA0OTM3NTY3OX0.Qx-Q7XT0X8F_BHWmL5p7Gb-BpNbvUxxDtjG8sgR0HPE"
                 "apikey" "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InhmbXd3YXF5cGppcWFscGdxYW1yIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzM3OTk2NzksImV4cCI6MjA0OTM3NTY3OX0.Qx-Q7XT0X8F_BHWmL5p7Gb-BpNbvUxxDtjG8sgR0HPE"
                 "Content-Type" "application/json"}]
    (try
      (println "entro no try do request")
      (let [response (client/get url {:headers headers :as :json})]
        (println "Resposta da API Supabase:" response) ;; Log para depuração
        (if (or (nil? (:body response)) (empty? (:body response)))
          {:status 404 :message "Usuário não encontrado"}
          {:status 200
           :message "Usuário autenticado com sucesso"
           :data (first (:body response))})) ;; Retorna o primeiro usuário encontrado
      (catch Exception e
        {:status 500 :message "Erro ao buscar usuário" :error (.getMessage e)}))))

