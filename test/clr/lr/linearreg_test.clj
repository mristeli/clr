(ns clr.lr.linearreg-test
  (:require [clojure.test :refer :all]
            [clr.lr.linearreg :refer :all]))

;;
;user=> (let [y 1] 
;; (def z y) 
;;   y)
;; 1
;; (deftest init-model-test
;;   "Initialize empty model before training"
;;   (let [model-size 6]
;;     (testing "Model is initialized to correct size"
;;       (is (= (count (init-model model-size)) model-size)))))

;; (deftest train-model-test "Test model return"
;;   (let [model (init-model 5)]
;;     (testing "Model is not returned"
;;       (is (= model (feed model [1 2 3 4 5 1000]))))
;;   )
;; )
(deftest predict-test-invalid "Invalid test argument"
  (testing "Prediction return -1 for wrong test input lenght"
    (is (= -1 (predict [2 3 4 5] 0 [2 3])))))

(deftest predict-test "Test model prediction"
  (testing "Prediction doesnt' match precalc."
    (is (= 3527 (predict [1 1 2 3 4] 0 [2000 1500 1 3 4])))))

;;
;; Simple test stuff: [1 1 1] -> 3
;;  [2 2 2] -> 6
;; With coeffs [1 1 1] -> squared error 0
(deftest squared-error-for-coeffs-test "Test squared error"
  (let [x-train [[1 1 1] [2 2 2]] y-train [3 6] coeffs [1 1 1]
        x-train2 [[1 4 5] [2 5 4]] y-train2 [10 10]]
    (testing "Squared error for simple lists works w/o error"
      (is (= 0 (squared-error-for-coeffs x-train y-train coeffs))))
    (testing "Squared error for simple lists works for one error"
      (is (= 1 (squared-error-for-coeffs x-train2 y-train2 coeffs))))))

;; (deftest simple-gradient-descent-test
;;   "Test x 1 -> y 1 gd lr blr"
;;   (let [x-train [[1 2]] y-train [3]]
;;     (testing "X 1 -> Y 1 should yield coeffs [1] intercept 0"
;;       (is (= (gradient-descent x-train y-train 0.001 1000) [[1] 0])))))
