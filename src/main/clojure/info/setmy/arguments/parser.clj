(ns info.setmy.arguments.parser
    "Argument parsing functionality."
    (:gen-class)
    (:require
        [clojure.tools.cli :refer [parse-opts]]))

(defn argument-parser-config
    [args-config]
    [["-d"
      "--directory DIRECTORY"
      "Directory to index into DB."
      :validate
      [(fn [value] (not (nil? value))) "Path must be set"]]
     ["-n"
      "--number NUMBER"
      "Number"
      :multi    false
      :default  80
      :parse-fn #(Integer/parseInt %)
      ; (fn [x] (Integer/parseInt x))
      :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]
     ; (fn [x] (< 0 x 0x10000))
     ["-i"
      "--indexing DB_NAME"
      "DB name."
      :default
      "indexing"]
     ["-p"
      "--profiles PROFILE_ONE,PROFILE_TWO"
      "Profiles."]
     ["-v"
      nil
      "Verbosity level"
      :id        :verbosity
      :default   0
      :update-fn inc]
     ["-h" "--help"]])

(defn parse-arguments
    [args args-config]
    (parse-opts args (argument-parser-config args-config)))
