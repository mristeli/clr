(ns clr.core
  (:gen-class)
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [ring.middleware.json :refer :all]
            [ring.util.response :refer :all]
            [clojure.string :as str]
            [clojure.data.json :as json]
            [clr.lr.simple-linearreg :as slr]))
;; https://medium.com/swlh/building-a-rest-api-in-clojure-3a1e1ae096e

(defn get-parameter-as-int-list
  "Get parameter as int list"
  [req key]
  (-> (:params req)
      (get key)
      (str/split #" ")
      (->> (map #(Integer/parseInt %)))))

(defn predict [req]
  (let [p (partial get-parameter-as-int-list req)
        result (slr/predict (p :input) (:coeff (:params req)))]
    (-> (response {:result result})
        (header "Content-Type" "application/json")
        (status 200))))

(defn gradient-descent
  [req]
  (let [p (partial get-parameter-as-int-list req)
        inputs (p :inputs) outputs (p :outputs)
        result (slr/gradient-descent inputs outputs)] ;; fetch get params & run gradient descent
    (-> (response {:result result})
        (header "Content-Type" "application/json")
        (status 200))))

(defroutes app-routes
  (GET "/api/predict" [] predict)
  (GET "/api/gd" [] gradient-descent)
  (route/not-found "Error, page not found!"))

(defn -main
  "Run web server at env PORT"
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    ; Run the server with Ring.defaults middleware
    (-> (wrap-defaults #'app-routes site-defaults)
        (wrap-json-response)
        (wrap-json-body {:keywords? true :bigdecimals? true})
        (server/run-server {:port port}))

    ; Run the server without ring defaults
    ;(server/run-server #'app-routes {:port port})


    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))
