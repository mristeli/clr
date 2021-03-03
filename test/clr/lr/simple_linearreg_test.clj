(ns clr.lr.simple-linearreg-test
  (:require [clojure.test :refer :all]
            [clr.lr.simple-linearreg :refer :all]))

;; (deftest first-test
;;   "Test simple 1 -> 1"
;;   (testing "X: 1 Y:1 should yield 1 0"
;;     (is (= (gradient-descent [1] [1] 0.01 1000) [1 0]))))

;;(deftest pseudo-hours-to-test-result-test
;;  "Test 1 -> 1"
;;  (testing "X: 1 Y:1 should yield 1 0"
;;    (is (= (gradient-descent [1 3 4] [1.5 4.7 6] 0.01 1000) [1.4 0.03]))))
