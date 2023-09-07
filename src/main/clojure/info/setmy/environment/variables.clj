(ns info.setmy.environment.variables
    "Environment variables functionality."
    (:gen-class)
    (:require [clojure.string :as str]
              [info.setmy.string.operations :refer :all]))

(defn set-environment-variable
    [variable-name variable-value]
    (throw
        (java.lang.UnsupportedOperationException. "Setting system environment variable is not implemented.")))

(defn get-environment-variable
    [variable-name]
    (System/getenv variable-name))

(defn delete-environment-variable
    [variable-name]
    (throw
        (java.lang.UnsupportedOperationException. "Deleting system environment variable is not implemented.")))

(defn get-boolean-environment-variable
    [variable-name]
    (to-boolean (get-environment-variable (variable-name))))

(defn get-short-environment-variable
    [variable-name]
    (to-short (get-environment-variable (variable-name))))

(defn get-int-environment-variable
    [variable-name]
    (to-int (get-environment-variable (variable-name))))

(defn get-long-environment-variable
    [variable-name]
    (to-long (get-environment-variable (variable-name))))

(defn get-float-environment-variable
    [variable-name]
    (to-float (get-environment-variable (variable-name))))

(defn get-double-environment-variable
    [variable-name]
    (to-double (get-environment-variable (variable-name))))

(defn get-json-environment-variable
    [variable-name]
    (json-to-object (get-environment-variable (variable-name))))

(defn get-environment-variables-list
    [variable-name & [parse-function]]
    (let []
        (if (nil? variable-name)
            []
            (let [trimmed-list (trim-list
                                (str/split (nil-to-default (get-environment-variable variable-name))
                                           (re-pattern comma-string)))]
                (if (nil? parse-function)
                    trimmed-list
                    (map-indexed parse-function trimmed-list))))))
