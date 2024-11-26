(ns my-clojure-api.core
  (:gen-class :require [org.httpkit.server :refer [run-server]]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn app [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    (str "Salve He4rt Developers <3")})
