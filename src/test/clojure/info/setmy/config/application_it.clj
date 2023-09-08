(ns info.setmy.config.application-it
    (:import [org.apache.logging.log4j LogManager])
    (:require [clojure.test :refer :all]
              [info.setmy.config.application :refer :all]
              [info.setmy.arguments.argument-config :refer :all]
              [info.setmy.string.operations :as str-ops]
              [info.setmy.arguments.config :refer :all]))

(def log (LogManager/getLogger (str (ns-name *ns*))))

(deftest init-default-test
    (testing "Arguments parsings default config"
             (let [args               ["sub-command" "any-other-param"]
                   description        "Test CLI"
                   arguments-config   [smi-profiles-argument
                                       smi-config-paths
                                       (->ArgumentConfig "some-other PLACEHOLDER" "s" str-ops/split-and-trim "Explanation." false)
                                       (->ArgumentConfig "another-other PLACEHOLDER" "a" str-ops/split-and-trim "Explanation." true)]
                   config             (->Config description arguments-config)]
                 (let [result                           (init args config)
                       merged-configuration             (:merged-configuration result)
                       merged-configuration-keys-number (count (keys merged-configuration))
                       name                             (:name merged-configuration)
                       a                                (:a merged-configuration)]
                     (is
                      (= name "./test/resources/application.yaml"))
                     (is
                      (= merged-configuration-keys-number 2))
                     (.info log "Message {}" "This is message")
                     (println "=====" result)))))

(deftest init-cli-test
    (testing "Arguments parsings by CLI config"
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
                 (let [result                           (init args config)
                       merged-configuration             (:merged-configuration result)
                       merged-configuration-keys-number (count (keys merged-configuration))
                       name                             (:name merged-configuration)
                       a                                (:a merged-configuration)
                       g                                (:g merged-configuration)]
                     #_(println ":applications-files-contents" (:applications-files-contents result))
                     (is
                      (= name "./test/resources/cli/application.yaml"))
                     (is
                      (= merged-configuration-keys-number 3))))))
