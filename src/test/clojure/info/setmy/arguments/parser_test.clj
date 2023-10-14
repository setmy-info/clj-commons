(ns info.setmy.arguments.parser-test
    (:require [clojure.test :refer :all]
              [info.setmy.arguments.parser :refer :all]
              [info.setmy.arguments.argument-config :refer :all]
              [info.setmy.arguments.constants :refer :all]
              [info.setmy.arguments.config :refer :all]
              [info.setmy.string.operations :as str-ops]
              [clojure.string :as str]))

(deftest parse-arguments-test
    (testing "Arguments parsings"
             (let [args               ["sub-command"
                                       "-i"
                                       "input.txt"
                                       "-o"
                                       "output.txt"
                                       "--smi-profiles"
                                       "profile1,profile2"
                                       "--smi-config-paths"
                                       "./src/test/resourses,./src/main/resourses"]
                   description        "Test CLI"
                   arguments-config   [smi-profiles-argument
                                       smi-config-paths-argument
                                       (->ArgumentConfig "some-other PLACEHOLDER" "s" str-ops/split-and-trim "Explanation." false)
                                       (->ArgumentConfig "another-other PLACEHOLDER" "a" str-ops/split-and-trim "Explanation." true)]
                   config             (->Config description arguments-config)]
                 (let [parsed (parse-arguments args config)]
                     (is
                      (= parsed
                         {:options   {:smi-profiles     '("profile1" "profile2"),
                                      :smi-config-paths '("./src/test/resourses" "./src/main/resourses")},
                          :arguments ["sub-command" "input.txt" "output.txt"],
                          :summary   "  -p, --smi-profiles COMMA_SEPARATED_PROFILES_LIST     Comma separated profiles string.\n  -c, --smi-config-paths COMMA_SEPARATED_CONFIG_PATHS  Comma separated config paths.\n  -s, --some-other PLACEHOLDER                         Explanation.\n  -a, --another-other PLACEHOLDER                      Explanation.",
                          :errors    ["Unknown option: \"-i\"" "Unknown option: \"-o\""]}))))))
