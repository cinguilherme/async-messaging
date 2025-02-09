(defproject org.clojars.cinguilherme/async-messaging "0.0.2-SNAPSHOT"

  :description "Abstration protocol and components to be used in system map defined by stuart sierra component system"

  :url "https://github.com/cinguilherme/async-messaging"

  ;; license is MIT
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}

  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/core.async "1.6.681"]
                 [com.stuartsierra/component "1.1.0"]
                 [org.clojure/spec.alpha "0.3.218"]
                 [nubank/state-flow "2.2.4"]
                 [prismatic/schema "1.4.1"]

                 ;; messaging systems abstraction Libs
                 [com.taoensso/carmine "3.4.1"]

                 [com.novemberain/langohr "5.5.0"] ;; RabbitMQ

                 [clj-nats-streaming "0.1.0-SNAPSHOT"] ;; NATS Streaming
                 [no.cjohansen/clj-nats "0.2025.02.07"]] ;; NATS Streaming

  :repl-options {:init-ns async-messaging.core})
