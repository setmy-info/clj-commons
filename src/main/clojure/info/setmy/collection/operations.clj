(ns info.setmy.collection.operations
    "Environment variables functionality."
    (:gen-class))

(defn apply-concat-many [& args]
    (apply concat args))

(defn product
    "Product function can be also for example + and str (concatenate strings)"
    [list-a list-b & [product-function]]
    (let [product-function (or product-function *)]
        (for [x list-a
              y list-b]
            (product-function x y))))

(defn product-as-pairs [dimension-a dimension-b]
    (for [x dimension-a
          y dimension-b]
        [x y]))
