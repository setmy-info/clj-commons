(ns info.setmy.json.parser
    "JSON parsing functionality."
    (:gen-class)
    (:require [clojure.string :as str]
              [clojure.data.json :as json]
              [info.setmy.file.operations :as file-ops]))

(defn parse-json-file
    [file-name & [post-actions]]
    (let [post-actions        (if-not (nil? post-actions) post-actions {})
          post-read-function  (or (:post-read-function post-actions) identity)
          post-parse-function (or (:post-parse-function post-actions) identity)]
        (try
            (-> file-name
                (file-ops/read-file)
                post-read-function
                (json/read-str)
                post-parse-function)
            (catch Exception e nil))))
