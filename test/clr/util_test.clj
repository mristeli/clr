(ns clr.util-test
  (:require [clojure.test :refer :all]
            [clr.util :refer :all]))

(deftest zip-test "Simple zipper test"
  (testing "Two lists are zipped correctly"
    (do
      (println (zip [1 2 3] [4 5 6]))
      (is (= [[1 4] [2 5] [3 6]] (zip [1 2 3] [4 5 6]))))))

(deftest zip-test2 "Three list zip test"
  (testing "Two lists are zipped correctly"
    (do
      (println (zip [1 2 3] [4 5 6] [7 8 9]))
      (is (= [[1 4 7] [2 5 8] [3 6 9]] (zip [1 2 3] [4 5 6] [7 8 9]))))))

(deftest param-as-int-list-test
  (testing "Params as int list"
    (let [req {:params {:inputs "1 2 3 4 5 6 7"}}]
      (is (= (get-parameter-as-int-list req :inputs) [1 2 3 4 5 6 7])))))

(deftest param-as-int
  (testing "Param as int list when param is not defined"
    (let [req {:params {:iterations "104"}}]
      (is (= (get-parameter-as-int req :iterations "100") 104)))))

(deftest param-as-double
  (testing "Param as int list when param is defined"
    (let [req { :params { :l-rate "0.01" } } ]
      (is (= (get-parameter-as-double req :l-rate "0.02") 0.01)))))