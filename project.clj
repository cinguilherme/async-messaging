(defproject org.clojars.cinguilherme/async-messaging "0.0.4-SNAPSHOT"

  :description "Abstration protocol and components to be used in system map defined by stuart sierra component system"

  :url "https://github.com/cinguilherme/async-messaging"

  ;; license is MIT
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}

  :repositories [["clojars" "https://clojars.org/repo/"]
                 ["maven-central" "https://repo1.maven.org/maven2/"]
                 ["confluent" "https://packages.confluent.io/maven/"]]

  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/core.async "1.6.681"]
                 [org.clojure/spec.alpha "0.3.218"]

                 [com.stuartsierra/component "1.1.0"]

                 [nubank/state-flow "2.2.4"]
                 [prismatic/schema "1.4.1"]

                 ;; messaging systems abstraction Libs
                 [com.taoensso/carmine "3.4.1"]
                 [com.taoensso/timbre "6.3.1"]

                 [com.novemberain/langohr "5.5.0"] ;; RabbitMQ
                 [no.cjohansen/clj-nats "0.2025.02.07"] ;; NATS Streaming


                 ;; will remove latter
                 [medley/medley "1.3.0"]
                 [clj-time/clj-time "0.15.2"]
                 [criterium/criterium "0.4.6"]
                
                 ;; loggings
                 [org.clojure/tools.logging "1.3.0"]
                 [org.apache.logging.log4j/log4j-api "2.17.1"]
                 [org.apache.logging.log4j/log4j-core "2.17.1"]
                 [org.apache.logging.log4j/log4j-slf4j-impl "2.17.1"]] 

  :repl-options {:init-ns async-messaging.core})
