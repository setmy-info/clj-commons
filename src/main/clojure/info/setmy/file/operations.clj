(ns info.setmy.file.operations
    "File operations functionality."
    (:gen-class)
    (:require [info.setmy.string.operations :refer :all]))

(defn read-file
    [file-name & [error-return]]
    (let [error-return (or error-return empty-string)]
        (try
            (with-open [file (clojure.java.io/reader file-name)]
                (slurp file))
            (catch java.io.FileNotFoundException _ error-return))))
