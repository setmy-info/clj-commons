(ns info.setmy.string.operations
    "String operations/functionality."
    (:gen-class)
    (:require [clojure.string :as str]
              [clojure.data.json :as json]
              [clj-yaml.core :as yaml]))

(def comma_delimiter ",")
(def empty_string "")

(defn split-and-trim
    [text & [delimiter]]
    (let [separator (or delimiter comma_delimiter)]
        (->> (str/split text (re-pattern (str separator)))
             (map str/trim))))

(defn trim_list
    [strings-list]
    (map str/trim strings-list))

(defn to-boolean
    [text & [default-value]]
    (let [default-value (or default-value false)]
        (if (nil? text)
            default-value,
            (let [lower-text (str/lower-case text)]
                (cond
                 (or (= lower-text "false") (= lower-text "no")) false
                 (or (= lower-text "true") (= lower-text "yes")) true
                 :else                                           (throw (ex-info "Invalid boolean value" {})))))))

(defn to-int
    [text & [default-value]]
    (let [default-value (or default-value 0)]
        (if (nil? text)
            default-value
            (try
                (Integer/parseInt text)
                (catch NumberFormatException _ default-value)))))

(defn to-short
    [text & [default-value]]
    (let [default-value (or default-value 0)]
        (if (nil? text)
            default-value
            (try
                (Short/parseShort text)
                (catch NumberFormatException _ default-value)))))

(defn to-float
    [text & [default-value]]
    (let [default-value (or default-value 0.0)]
        (if (nil? text)
            default-value
            (try
                (Float/parseFloat text)
                (catch NumberFormatException _ default-value)))))

(defn to-double
    [text & [default-value]]
    (let [default-value (or default-value 0.0)]
        (if (nil? text)
            default-value
            (try
                (Double/parseDouble text)
                (catch NumberFormatException _ default-value)))))

(defn json-to-object
    [text & [default-value]]
    (let [default-value (or default-value {})]
        (if (nil? text)
            default-value
            (json/read-str text))))


(defn yaml-to-object
    [text & [default-value]]
    (let [default-value (or default-value {})]
        (if (nil? text)
            default-value
            (yaml/parse-string text))))

(defn find-named-placeholders
    [text & [as-clean]]
    (let [as-clean            (if (nil? as-clean) true as-clean)
          pattern             (re-pattern "\\$\\{(.*?)\\}")
          placeholders        (re-seq pattern text)
          unique-placeholders (distinct placeholders)]
        (if as-clean
            ; placeholder is in form: [["${def}" "def"] ["${jkl}" "jkl"] ["${prs}" "prs"]]
            (map second unique-placeholders)
            (map first unique-placeholders))))

(defn replace-named-placeholder
    [text place-holder-name replacement]
    (let [replacement-string (if (nil? replacement) "" replacement)
          placeholder        (str "${" place-holder-name "}")]
        (-> text
            (str/replace (re-pattern (str (java.util.regex.Pattern/quote placeholder))) replacement-string))))

(defn combined-list
    [list1 list2 & [join-text]]
    (let [join-text (or join-text empty_string)]
        (map #(apply str %)
             (for [item1 list1
                   item2 list2]
                 (str item1 join-text item2)))))

(defn combined-by-function-list
    [list1 list2 & [join-text func]]
    (let [join-text (or join-text empty_string)
          result    (atom [])]
        (doseq [item1 list1
                item2 list2]
            (let [sum-item (str item1 join-text item2)]
                (when (and (not (nil? func)) (func sum-item))
                      (swap! result conj sum-item))))
        @result))

(defn nil-to-default
    [text & [default-text]]
    (let [default-text  (or default-text empty_string)]
        (if (nil? text)
            default-text
            text)))
