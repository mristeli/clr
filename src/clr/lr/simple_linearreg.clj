(ns clr.lr.simple-linearreg
  (:gen-class)
  (:require [clojure.math.numeric-tower :as math]))

(defn predict
  "Apply the coefficients in the model to inputs in test values.
   Output a scalar prediction"
  ([coeff test-inputs] (predict coeff 0 test-inputs))
  ([coeff intercept test-input]
   (+ (* coeff test-input) intercept)))

(defn squared-error-for-coeffs
  "Calculate squared error for predictions"
  ([x-train y-train coeff] (squared-error-for-coeffs x-train y-train coeff 0))
  ([x-train y-train coeff intercept]
   (let [predictions (map #(predict coeff intercept %) x-train)
         errors (map - predictions y-train)]
     (reduce + (map #(math/expt % 2) errors)))))

(defn mean-squared-error
  "Squared error divided by sample count"
  [x-train y-train coeff intercept]
  (/ (squared-error-for-coeffs x-train y-train coeff intercept) (count x-train)))

(defn- d-coeff
  "Partial defivative of coeffs"
  [x-train y-train predictions]
  (let [sample-count (count x-train)
        errors (map - y-train predictions)
        xyp (reduce + (map * x-train errors))]
    (* (/ (- 2) sample-count) xyp)))

(defn- d-intercept
  "Partial defivative of intercept"
  [x-train y-train predictions]
  (let [sample-count (count x-train)
        errors (reduce + (map - y-train predictions))]
    (* (/ (- 2) sample-count) errors)))

(defn- update-value
  "Get next guess for value"
  [value l-rate d]
  (- value (* l-rate d)))

(defn gradient-descent
  "Gradient descent to find coeffs & intercept for Linear Regression"
  ([x-train y-train] (gradient-descent x-train y-train 0.1 1000))
  ([x-train y-train l-rate epochs]
   (let [random-max (apply max y-train)
         coeff 0
         intercept 0]
     (loop [round 0 i-coeff coeff i-intercept intercept]
       (let [preds (map #(predict i-coeff i-intercept %) x-train)
             errors (map - y-train preds)
             dm (d-coeff x-train y-train preds)
             db (d-intercept x-train y-train preds)]
         (println i-coeff i-intercept errors)
         (if (< round epochs)
           (recur (inc round)
                  (update-value i-coeff l-rate dm)
                  (update-value i-intercept l-rate db))
           [i-coeff i-intercept]))))))


;;;
;; prepare a seq of the even values 
;; from the first six multiples of three
;; (for [x [0 1 2 3 4 5]
;;   :let [y (* x 3)]
;;   :when (even? y)]
;; y)
;;=> (0 6 12)
