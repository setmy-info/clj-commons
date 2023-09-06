(ns info.setmy.arguments.argument
    "Argument class."
    (:gen-class)
    (:require [info.setmy.string.operations :as str-ops]))

(defrecord Argument [name short-flag argument-type argument-help required])

(def smi-profiles-argument
    (->Argument "smi-profiles" "p" str-ops/split-and-trim "Comma separated profiles string." false))

(def smi-config-paths
    (->Argument "smi-config-paths" "c" str-ops/split-and-trim "Comma separated config paths." false))

