(ns info.setmy.arguments.parser
    "Argument parsing functionality."
    (:gen-class)
    (:require
        [clojure.tools.cli :refer [parse-opts]]))

(defn map-arguments-config
    [x]
    [(str "-" (:short-flag x))
     (str "--" (:name x))
     (:argument-help x)
     :parse-fn
        (:argument-type x)
     :validate
        [(if (:required x)
             (fn [value] (if (nil? value) false true))
             (fn [value] true))]])


(defn argument-parser-config
    [args-config]
    (let [description             (:description args-config)
          arguments-config        (:arguments-config args-config)
          mapped-arguments-config (map map-arguments-config arguments-config)]
        mapped-arguments-config))

(defn parse-arguments
    [args args-config]
    (parse-opts args (argument-parser-config args-config)))
