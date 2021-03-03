(ns clr.util (:gen-class))

(defn zip
  "Standardy Python-like zip"
  [& lists]
  (defn iter
    [i-lists]
    (if (some empty? i-lists)
      '()
      (cons (map first i-lists) (iter (map rest i-lists)))))
  (iter lists))
