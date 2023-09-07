(ns info.setmy.config.application-it
    (:require [clojure.test :refer :all]
              [info.setmy.config.application :refer :all]
              [info.setmy.arguments.argument-config :refer :all]
              [info.setmy.string.operations :as str-ops]
              [info.setmy.arguments.config :refer :all]))

(deftest init-test
    (testing "Arguments parsings"
             (let [args               ["sub-command"
                                       "-i"
                                       "input.txt"
                                       "-o"
                                       "output.txt"
                                       "--smi-profiles"
                                       "profile1,profile2"
                                       "--smi-config-paths"
                                       "./src/test/resources/cli"]
                   description        "Test CLI"
                   arguments-config   [smi-profiles-argument
                                       smi-config-paths
                                       (->ArgumentConfig "some-other PLACEHOLDER" "s" str-ops/split-and-trim "Explanation." false)
                                       (->ArgumentConfig "another-other PLACEHOLDER" "a" str-ops/split-and-trim "Explanation." true)]
                   config             (->Config description arguments-config)]
                 (let [result (init args config)]
                     (println "==== RESULT ====" result)
                     (comment (is
                      (= result [])))))))
