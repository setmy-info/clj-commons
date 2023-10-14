(ns info.setmy.config.application
    "Environment variables functionality."
    (:gen-class)
    (:require [info.setmy.arguments.parser :as arg-parser]
              [info.setmy.environment.variables :as env-variables]
              [info.setmy.collection.operations :as collection-ops]
              [info.setmy.string.operations :as string-ops]
              [info.setmy.config.constants :refer :all]
              [info.setmy.yaml.parser :as yaml-parser]
              [info.setmy.json.parser :as json-parser]
              [clojure.string :as str]))

(defn get-cli-name
    [keyword arguments]
    (let [options                (:options arguments)
          result                 (keyword options)]
        result))

(defn get-cli-config-paths
    [keyword arguments]
    (let [options                (:options arguments)
          result                 (keyword options)]
        (if (nil? result) [] result)))

(defn get-cli-optional-config-files
    [keyword arguments]
    (let [options                (:options arguments)
          result                 (keyword options)]
        (if (nil? result) [] result)))

(defn find-last-not-none-and-empty [& args]
    (last (filter (fn [arg] (and (not (nil? arg)) (not (empty? arg)))) args)))

(defn post-read-function [text]
    (let [placeholders            (string-ops/find-named-placeholders text)
          placeholder-value-pairs (map
                                   (fn [placeholder]
                                       [placeholder (env-variables/get-environment-variable placeholder)])
                                   placeholders)]
        (loop [modified-text   text
               remaining-pairs placeholder-value-pairs]
            (if (empty? remaining-pairs)
                modified-text
                (let [placeholder-value-pair (first remaining-pairs)
                      placeholder            (first placeholder-value-pair)
                      value                  (second placeholder-value-pair)
                      new-text               (if value
                                                 (string-ops/replace-named-placeholder modified-text placeholder value)
                                                 modified-text)]
                    (recur new-text (rest remaining-pairs)))))))

(defn parse-file-by-type [file-name]
    (let [new-file-name (clojure.string/lower-case file-name)]
        (cond
         (re-matches #".+\.(yaml|yml)$" new-file-name)
         (yaml-parser/parse-yaml-file file-name {:post-read-function post-read-function})

         (re-matches #".+\.json$" new-file-name)
         (json-parser/parse-json-file file-name {:post-read-function post-read-function})

         :else
         nil)))

(defn applications-files-paths-parsing
    [config-paths
     application-files
     optional-env-application-files
     optional-cli-application-files]
    (let [is-file?                (fn [file-path] (.isFile (java.io.File. file-path)))
          combined-list           (string-ops/combined-by-function-list config-paths application-files "/" is-file?)
          concatenated-files-list (concat combined-list optional-env-application-files optional-cli-application-files)
          result-list             (map (fn [item] [item (parse-file-by-type item)]) concatenated-files-list)]
        result-list))

(defn merge-maps [left right]
    (merge-with (fn [left right] right) left right))

(defn merge-config [applications-files-contents]
    (reduce (fn [x y] (merge-maps x y))
            {}
            (map (fn [pair] (second pair)) applications-files-contents)))

(defn get-config-app-name
    [config]
    (let [application (:application config)]
        (if (not (nil? application))
            (:name application)
            nil)))

(defn init [args args-config]
    (let [arguments                           (arg-parser/parse-arguments args args-config)
          env-config-paths                    (env-variables/get-environment-variables-list smi-config-paths)
          cli-config-paths                    (get-cli-config-paths :smi-config-paths arguments)
          env-profiles                        (env-variables/get-environment-variables-list smi-profiles)
          cli-profiles                        (get-cli-config-paths :smi-profiles arguments)
          config-paths                        (collection-ops/apply-concat-many
                                               ["./src/main/resources"
                                                "./main/resources"
                                                "./resources"]
                                               ["./src/test/resources"
                                                "./test/resources"]
                                               env-config-paths
                                               cli-config-paths)
          profiles-list                       (find-last-not-none-and-empty env-profiles cli-profiles)
          default-application-files           (string-ops/combined-list application-file-prefixes application-file-suffixes ".")
          application-profiles-file-prefixes  (string-ops/combined-list application-file-prefixes profiles-list "-")
          application-profiles-files          (string-ops/combined-list application-profiles-file-prefixes application-file-suffixes ".")
          application-files                   (collection-ops/apply-concat-many default-application-files application-profiles-files)
          optional-env-application-files      (env-variables/get-environment-variables-list smi-optional-config-files)
          optional-cli-application-files      (get-cli-optional-config-files :smi-optional-config-files arguments)
          applications-files-contents         (applications-files-paths-parsing config-paths application-files optional-env-application-files optional-cli-application-files)
          merged-configuration                (merge-config applications-files-contents)
          name                                (or (get-cli-name :smi-name arguments)
                                                  (env-variables/get-environment-variable smi-name)
                                                  (get-config-app-name merged-configuration)
                                                  "default")]
        {:env-profiles                       env-profiles
         :cli-profiles                       cli-profiles
         :env-config-paths                   env-config-paths
         :cli-config-paths                   cli-config-paths
         :config-paths                       config-paths
         :profiles-list                      profiles-list
         :default-application-files          default-application-files
         :application-profiles-file-prefixes application-profiles-file-prefixes
         :application-profiles-files         application-profiles-files
         :application-files                  application-files
         :applications-files-contents        applications-files-contents
         :merged-configuration               merged-configuration}))
