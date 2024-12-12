(ns my-clojure-api.server)

(def supabase-config
  {:url "https://xfmwwaqypjiqalpgqamr.supabase.co"
   :api-key "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InhmbXd3YXF5cGppcWFscGdxYW1yIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzM3OTk2NzksImV4cCI6MjA0OTM3NTY3OX0.Qx-Q7XT0X8F_BHWmL5p7Gb-BpNbvUxxDtjG8sgR0HPE"})

(def news-config
  {:url "https://api.nytimes.com/svc/news/v3/content/all/all.json"
   :api-key "1TH9vj0F5Wy6fgJRKAWcVnGbKAIsSCLT"})