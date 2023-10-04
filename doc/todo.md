# Introduction to clj-commons

    "
    Parses a YAML file located at the specified file path.

    Args file-name (str) The path to the YAML file to parse.

    post-actions (map, optional) A map containing post-processing functions for ':post-read-function' and ':post-parse-function'. Defaults to an empty map.

    Returns Any The parsed YAML data after applying the specified post-processing functions. If an error occurs during parsing or file reading, nil is returned.

    Example Usage (def post-actions {:post-read-function clojure.string/lower-case :post-parse-function (fn [parsed] (select-keys parsed [:name :city]))})

    (def parsed-data (parse-yaml-file \"data.yaml\" :post-actions post-actions))
    "

...

    "
    Parses a JSON file located at the specified file path.

    **Args**

    file-name (str) The path to the JSON file to parse.

    post-actions (map, optional) A map containing post-processing functions for ':post-read-function' and ':post-parse-function'. Defaults to an empty map.

    Returns

    Any The parsed JSON data after applying the specified post-processing functions. If an error occurs during parsing or file reading, nil is returned.

    Example Usage

    (def post-actions {:post-read-function clojure.string/lower-case :post-parse-function (fn [parsed] (select-keys parsed [:name :city]))})

    (def parsed-data (parse-json-file \"data.json\" :post-actions post-actions))
    "

...

```clojure
(defn load-properties-from-classpath [filename]
    (comment
     (let [classloader (Thread/currentThread)
           .getContextClassLoader
           properties
           (Properties.)]
         (with-open [stream (.getResourceAsStream classloader filename)]
             (if stream
                 (do
                     (.load properties stream)
                     (set-application-properties properties)
                     properties)
                 (throw (Exception. (str "File " filename " not found on CLASSPATH"))))))))
```

```clojure
(defonce os-path-separator (System/getProperty "file.separator"))
```
