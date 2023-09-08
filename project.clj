(defproject info.setmy/clj-commons "0.2.0-SNAPSHOT"
    :description "Commons functionality for Clojure projects."
    :url "https://github.com/setmy-info/clj-commons"
    :license
    {:name "MIT License"
     :url  "https://opensource.org/licenses/MIT"}
    :dependencies [[org.clojure/clojure "1.11.1"]
                   [org.clojure/tools.cli "1.0.219"]
                   [org.clojure/data.json "2.4.0"]
                   [clj-commons/clj-yaml "1.0.27"]]
    :plugins [[lein-codox "0.10.8"]]
    :main ^:skip-aot info.setmy.commons
    :target-path "target/%s"
    :source-paths ["src/main/clojure"]
    :test-paths ["src/test/clojure"]
    :resource-paths ["src/main/resources" "./target/classes"]
    :profiles
    ; with-profile :abc
    {:uberjar {:aot      :all
               :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
     :local   {}
     :dev     {}
     :ci      {}
     :test    {}
     :prelive {}
     :live    {}}
    :deploy-repositories
    [["clojars"
      {:url           "https://clojars.org/repo"
       :sign-releases false}]]
    :aliases {"doc" ["do" "codox"]}
    :codox
    {:metadata {:doc/format :markdown}})
