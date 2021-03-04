(ns clr.core
  (:gen-class)
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [ring.middleware.json :refer :all]
            [ring.util.response :refer :all]
            [clojure.data.json :as json]
            [clr.lr.simple-linearreg :as slr]
            [clr.util :refer :all]))

;; https://medium.com/swlh/building-a-rest-api-in-clojure-3a1e1ae096e

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
        result (slr/gradient-descent inputs outputs)
        l-rate (get-parameter-as-double req :l-rate "0.01")
        iterations (get-parameter-as-int req :iterations "10000")]
    (-> (response {:gd_params {:inputs inputs
                               :outputs outputs
                               :l-rate l-rate
                               :iterations iterations}
                   :result { :coeff (first result) :intercept (second result) }})
        (header "Content-Type" "application/json")
        (status 200))))

(defroutes app-routes
  (GET "/api/predict" [] predict)
  (GET "/api/gd" [] gradient-descent)
  (route/not-found "Error, page not found!"))

(defn -main
  "Run web server at env PORT"
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3003"))]
    (-> (wrap-defaults #'app-routes site-defaults)
        (wrap-json-response)
        (wrap-json-body {:keywords? true :bigdecimals? true})
        (server/run-server {:port port}))
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))
