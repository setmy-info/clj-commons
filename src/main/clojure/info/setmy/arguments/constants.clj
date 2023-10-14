(ns info.setmy.arguments.constants
    "Arguments Constants."
    (:gen-class)
    (:require [info.setmy.arguments.argument-config :refer :all]
              [info.setmy.string.operations :as str-ops]))

(def smi-profiles-argument
    (->ArgumentConfig "smi-profiles COMMA_SEPARATED_PROFILES_LIST" "p" str-ops/split-and-trim "Comma separated profiles string." false))

(def smi-config-paths-argument
    (->ArgumentConfig "smi-config-paths COMMA_SEPARATED_CONFIG_PATHS" "c" str-ops/split-and-trim "Comma separated config paths." false))

(def smi-optional-config-files-argument
    (->ArgumentConfig "smi-optional-config-files COMMA_SEPARATED_CONFIG_FILES" "o" str-ops/split-and-trim "Comma separated config files." false))

(def smi-name-argument
    (->ArgumentConfig "smi-name APPLICATOIN_NAME" "o" str "Application name." false))
