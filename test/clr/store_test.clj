(ns clr.lr.linearreg-test
  (:require [clojure.test :refer :all]
            [clr.store :refer :all]))

(deftest new-store-test 
  "Test LR model store init"
  []
  (let [store (new-store)]
    (testing "New store is not empty" 
      (is (= (:size store) 0))
    )
  )
)


(deftest new-store-test 
  "Test add model to store"
  []
  (let [store (new-store) model1 (new-model "1" 5)]
    (testing "Add empty model to store" 
      
    )
  )
)
