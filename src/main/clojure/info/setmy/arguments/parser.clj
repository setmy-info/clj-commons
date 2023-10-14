(ns info.setmy.arguments.parser
    "Argument parsing functionality."
    (:gen-class)
    (:require
        [clojure.tools.cli :refer [parse-opts]]))

(defn map-arguments-config
    [argument-config]
    [(str "-" (:short-flag argument-config))
     (str "--" (:name argument-config))
     (:argument-help argument-config)
     :parse-fn
        (:argument-type-func argument-config)
     :validate
        [(if (:required argument-config)
             (fn [value] (if (nil? value) false true))
             (fn [value] true))]])


(defn argument-parser-config
    [args-config]
    (let [description             (:description args-config)
          argument-config         (:arguments-config args-config)
          mapped-arguments-config (map map-arguments-config argument-config)]
        mapped-arguments-config))

(defn parse-arguments
    [args args-config]
    (parse-opts args (argument-parser-config args-config)))
