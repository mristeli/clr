(defn new-store 
  []
  (atom [])
)

(defstruct model :name :size :train-x :train-y :coeffs)

(defn new-model 
  [store input-count]
  (add-model-to-store store (struct model (:size store) input-count [] [] []))  
)

(defn add-sample-to-model 
  [model inputs output]
  (let [train-x (:train-x model) train-y (:train-y model)]
    (-> model 
      (assoc :train-x (conj train-x inputs))
      (assoc :train-y (conj train-y output))
    )
  )
)

(defn add-model-to-store
  [store model]
  (let [models (:models store)]
     (-> store
       (assoc :models (conj models model))
       (update :size inc)
    )
  ) 
)
