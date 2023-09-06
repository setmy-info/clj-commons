(ns info.setmy.yaml.parser
    "YAML parsing functionality."
    (:gen-class)
    (:require [clj-yaml.core :as yaml]
              [info.setmy.file.operations :as file-ops]))

(defn parse-yaml-file
    [file-name & [post-actions]]
    (let [post-actions        (if-not (nil? post-actions) post-actions {})
          post-read-function  (or (:post-read-function post-actions) identity)
          post-parse-function (or (:post-parse-function post-actions) identity)]
        (try
            (-> file-name
                (file-ops/read-file)
                post-read-function
                (yaml/parse-string)
                post-parse-function)
            (catch Exception e nil))))
