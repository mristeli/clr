(ns clr.lr.linearreg
  (:gen-class)
  (:require [clr.util :refer :all])
  (:require [clojure.math.numeric-tower :as math]))

;;
;; Here might eventually be a working implementation 
;; of linear regression with multiple input dimensions
;;

(defn predict
  "Apply the coefficients in the model to inputs in test values.
   Output a scalar prediction"
  ([coeffs test-inputs] (predict coeffs 0 test-inputs))
  ([coeffs intercept test-inputs]
   (if (not (= (count coeffs) (count test-inputs)))
     -1 ;; |c[] != |input| is errorish, figure out later how to handle properl
     (+ intercept (reduce + (map * coeffs test-inputs))))))

(defn squared-error-for-coeffs
  "Calculate squared error for predictions"
  ([x-train y-train coeffs] (squared-error-for-coeffs x-train y-train coeffs 0))
  ([x-train y-train coeffs intercept]
   (let [predictions (map #(predict coeffs intercept %) x-train)
         errors (map - predictions y-train)]
     (reduce + (map #(math/expt % 2) errors)))))

(defn mean-squared-error
  "Squared error divided by sample count"
  [x-train y-train coeffs intercept]
  (/ (squared-error-for-coeffs x-train y-train coeffs intercept) (count x-train)))

(defn- d-coeffs
  "Partial defivative of coeffs"
  [x-train y-train predictions]
  (let [input-count (count (first x-train))
        errors (map - y-train predictions)
        xyp (reduce + (map #(map * % errors) x-train))]
    (map #(* (/ (- 2) input-count) %) xyp)))

(defn- d-intercept
  "Partial defivative of intercept"
  [x-train y-train predictions]
  (let [input-count (count (first x-train))
        errors (reduce + (map - y-train predictions))]
    (* (/ (- 2) input-count) errors)))

(defn- update-coeffs
  "Get next guess for coeffs m/x from l-rate and derivative"
  [coeffs l-rate dm]
  (map #(- % (* l-rate dm) coeffs)))

(defn- update-intercept
  "Get next guess for intercept b from l-rate and derivative"
  [intercept l-rate db]
  (- intercept (* l-rate db)))

(defn gradient-descent
  "Gradient descent to find coeffs & intercept for Linear Regression"
  ([x-train y-train] (gradient-descent x-train y-train 0.001 100))
  ([x-train y-train l-rate epochs]
   (let [random-max (apply max y-train)
         input-length (count (first x-train))
         coeffs (take input-length (repeatedly #(rand-int random-max)))
         intercept (rand-int random-max)
         l-rate 0.001
         epochs 100]
     (loop [round 0 i-coeffs coeffs i-intercept intercept]
       (let [preds (map #(predict i-coeffs i-intercept %) x-train)
             dm (d-coeffs x-train y-train preds)
             db (d-intercept x-train y-train preds)]
         (if (< round epochs)
           (recur (inc round)
                  (update-coeffs i-coeffs l-rate dm)
                  (update-intercept i-intercept l-rate db))
           [i-coeffs i-intercept]))))))

;; prepare a seq of the even values 
;; from the first six multiples of three
;; (for [x [0 1 2 3 4 5]
;;   :let [y (* x 3)]
;;   :when (even? y)]
;; y)
;;=> (0 6 12)
