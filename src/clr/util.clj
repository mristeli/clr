(ns clr.util
  (:require [clojure.string :as str]))

(defn zip
  "Standardy Python-like zip"
  [& lists]
  (defn iter
    [i-lists]
    (if (some empty? i-lists)
      '()
      (cons (map first i-lists) (iter (map rest i-lists)))))
  (iter lists))

(defn get-parameter-as-int-list
  "Get parameter as int list"
  [req key]
  (-> (:params req)
      (get key)
      (str/split #" ")
      (->> (map #(Integer/parseInt %)))))

(defn get-parameter-as-int
  "Get parameter value as int or return default as int if non-existing"
  [req key d-value]
  (if-let [p-value (get (:params req) key)]
    (Integer/parseInt p-value)
    d-value))

(defn get-parameter-as-double
  "Get parameter value as int or return default as int if non-existing"
  [req key d-value]
  (if-let [p-value (get (:params req) key)]
    (Double/parseDouble p-value)
    d-value))

