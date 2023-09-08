(ns info.setmy.arguments.argument-config
    "Argument class."
    (:gen-class)
    (:require [info.setmy.string.operations :as str-ops]))

(defrecord ArgumentConfig [name short-flag argument-type argument-help required])

(def smi-profiles-argument
    (->ArgumentConfig "smi-profiles COMMA_SEPARATED_PROFILES_LIST" "p" str-ops/split-and-trim "Comma separated profiles string." false))

(def smi-config-paths
    (->ArgumentConfig "smi-config-paths COMMA_SEPARATED_CONFIG_PATHS" "c" str-ops/split-and-trim "Comma separated config paths." false))
