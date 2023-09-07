(ns info.setmy.string.operations-test
    (:require [clojure.test :refer :all]
              [info.setmy.string.operations :refer :all]
              [clojure.string :as str]))

(deftest split-and-trim-test
    (testing "Spliting untrimmed coma separated values"
             (let [cleaned (split-and-trim " asdfg , bsg, csdg  , dsg  " comma-string)]
                 (is
                  (= cleaned ["asdfg" "bsg" "csdg" "dsg"])))
             (is
              (= (split-and-trim " asdfg , bsg, csdg  , dsg  ")
                 ["asdfg" "bsg" "csdg" "dsg"]))))

(deftest trim_list-test
    (testing "Strings list trimming"
             (is
              (= (trim-list '("a  " " b " "  c  " "   d"))
                 ["a" "b" "c" "d"])
              (= (trim-list ["a  " " b " "  c  " "   d"])
                 ["a" "b" "c" "d"]))))

(deftest to-boolean-test
    (testing "Boolean string"
             (is (= (to-boolean nil) false))
             (is (= (to-boolean nil nil) false))
             (is (= (to-boolean nil true) true))
             (is (= (to-boolean nil false) false))
             (is (= (to-boolean "False") false))
             (is (= (to-boolean "False" true) false))
             (is (= (to-boolean "True") true))
             (is (= (to-boolean "True" false) true))
             (is (= (to-boolean "yEs") true))
             (is (= (to-boolean "no") false))))

(deftest to-int-test
    (testing "Float string to integer"
             (is (= (to-int nil) 0))
             (is (= (to-int nil nil) 0))
             (is (= (to-int nil 1) 1))
             (is (= (to-int "123" 0) 123))
             (is (= (to-int "123.123" 0) 0))
             (is (= (to-int "blah" 222) 222))
             (is (= (to-int "123.123" 321) 321))
             (is (= (to-int "123.123") 0))))

(deftest to-float-test
    (testing "Float string to integer"
             (is (= (to-float nil) 0.0))
             (is (= (to-float nil nil) 0.0))
             (is (= (to-float nil 1.0) 1.0))
             (is (= (to-float "123" 0.0) 123.0))
             (is (= (to-float "123#123" 0.0) 0.0))
             (is (= (to-int "blah" 222.0) 222.0))
             (is (= (to-float "123#123" 321.0) 321.0))
             (is (= (to-float "123#123") 0.0))))

(defn maps-equal?
    [map-a map-b]
    (and (= (count map-a) (count map-b))
         (every? (fn [key] (maps-equal? (get map-a key) (get map-b key))) (keys map-a))))

(deftest json-to-object-test
    (testing "JSON to map conversion"
             (is (= (json-to-object nil) {}))
             (let [parsed (json-to-object "{\"name\":\"John Doe\", \"city\":\"Tallinn\"}")]
                 (println (type parsed))
                 (println "Parsed JSON : " parsed)
                 (is (= (get parsed "name") "John Doe"))
                 (is (= (get parsed "city") "Tallinn")))))

(deftest yaml-to-object-test
    (testing "YAML to map conversion"
             (is (= (yaml-to-object nil) {}))
             (let [parsed (yaml-to-object "person:\n    name: John Doe\n    city: Tallinn")
                   person (:person parsed)]
                 (println (type parsed))
                 (println "Parsed YAML : " parsed)
                 (println "Person" person)
                 (is (= (:name person) "John Doe"))
                 (is (= (:city person) "Tallinn")))))

(deftest find-named-placeholders-test
    (testing "Placeholders listing"
             (let [placeholders-text  "abc ${def} ghi ${jkl} mno ${prs} ${prs}"]
                 (is (= (find-named-placeholders placeholders-text) ["def" "jkl" "prs"]))
                 (is (= (find-named-placeholders placeholders-text true) ["def" "jkl" "prs"]))
                 (is
                  (= (find-named-placeholders placeholders-text false) ["${def}" "${jkl}" "${prs}"])))))

(deftest replace-named-placeholder-test
    (testing "Replacing in text all placeholders"
             (let [placeholders-text  "abc ${def} ghi ${jkl} mno ${prs} ${prs}"]
                 (let [result (replace-named-placeholder placeholders-text "jkl" "Hello World")]
                     (is (= result "abc ${def} ghi Hello World mno ${prs} ${prs}")))
                 (let [result (replace-named-placeholder placeholders-text "prs" "qwerty")]
                     (is (= result "abc ${def} ghi ${jkl} mno qwerty qwerty"))))))

(deftest combined-list-with-empty-b-test
    (testing "Joining arrays"
             (let [result (combined-list ["A" "B" "C"] [] ":")]
                 (is (= result [])))))

(deftest combined-list-with-empty-a-test
    (testing "Joining arrays"
             (let [result (combined-list [] ["X" "Y"] ":")]
                 (is (= result [])))))

(deftest combined-list-with-nil-item-b-test
    (testing "Joining arrays"
             (let [result (combined-list ["A" "B" "C"] [nil] ":")]
                 (is (= result [])))))

(deftest combined-list-with-nil-item-a-test
    (testing "Joining arrays"
             (let [result (combined-list [nil] ["X" "Y"] ":")]
                 (is (= result [])))))

(deftest combined-list-test
    (testing "Joining arrays"
             (let [result (combined-list ["A" "B" "C"] ["X" "Y"])]
                 (is (= result ["AX" "AY" "BX" "BY" "CX" "CY"])))
             (let [result (combined-list ["A" "B" "C"] ["X" "Y"] ":")]
                 (is (= result ["A:X" "A:Y" "B:X" "B:Y" "C:X" "C:Y"])))))

(deftest combined-list-test-2
    (testing "Joining arrays"
             (let [result (combined-list ["A" "B" "C"] ["X" "Y" nil])]
                 (is (= result ["AX" "AY" "BX" "BY" "CX" "CY"])))
             (let [result (combined-list ["A" "B" "C"] ["X" "Y" nil] ":")]
                 (is (= result ["A:X" "A:Y" "B:X" "B:Y" "C:X" "C:Y"])))))

(deftest combined-list-test-3
    (testing "Joining arrays"
             (let [result (combined-list ["A" "B" "C" nil] ["X" "Y"])]
                 (is (= result ["AX" "AY" "BX" "BY" "CX" "CY"])))
             (let [result (combined-list ["A" "B" "C" nil] ["X" "Y"] ":")]
                 (is (= result ["A:X" "A:Y" "B:X" "B:Y" "C:X" "C:Y"])))))

(deftest combined-by-function-list-test
    (testing "Joining arrays with function"
             (let [result (combined-by-function-list ["A" "B" "C"] ["X" "Y"] "" (fn [x] true))]
                 (is (= result ["AX" "AY" "BX" "BY" "CX" "CY"])))
             (let [result (combined-by-function-list ["A" "B" "C"] ["X" "Y"] ":" (fn [x] true))]
                 (is (= result ["A:X" "A:Y" "B:X" "B:Y" "C:X" "C:Y"])))
             (let [result (combined-by-function-list ["A" "B" "C"] ["X" "Y"] "" (fn [x] false))]
                 (is (= result [])))
             (let [result (combined-by-function-list ["A" "B" "C"] ["X" "Y"] ":" (fn [x] false))]
                 (is (= result [])))
             (let [result (combined-by-function-list ["A" "B" "C"] ["X" "Y"] "" (fn [x] (not (re-matches #".*[Cc].*[Yy].*" x))))]
                 (is (= result ["AX" "AY" "BX" "BY" "CX"])))
             (let [result (combined-by-function-list ["A" "B" "C"] ["X" "Y"] ":" (fn [x] (not (re-matches #".*[Cc].*[Yy].*" x))))]
                 (is (= result ["A:X" "A:Y" "B:X" "B:Y" "C:X"])))))

(deftest nil-to-default-list-test
    (testing "Nil replaced with non nil"
             (let [result (nil-to-default nil)]
                 (is (= result "")))
             (let [result (nil-to-default nil "X")]
                 (is (= result "X")))))
