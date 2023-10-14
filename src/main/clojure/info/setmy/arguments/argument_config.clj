(ns info.setmy.arguments.argument-config
    "Argument class."
    (:gen-class))

(defrecord ArgumentConfig [name short-flag argument-type-func argument-help required])
