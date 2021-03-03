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
