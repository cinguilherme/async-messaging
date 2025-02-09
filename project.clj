(defproject async-messaging "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                  [org.clojure/core.async "1.6.681"]
                  [com.stuartsierra/component "1.1.0"]
                  [org.clojure/spec.alpha "0.3.218"]
                  [nubank/state-flow "2.2.4"]
                  [prismatic/schema "1.4.1"]
                  [com.taoensso/carmine "3.4.1"]
                  [com.novemberain/langohr "5.5.0"]
                 
                  [clj-nats-streaming "0.1.0-SNAPSHOT"]
                  [no.cjohansen/clj-nats "0.2025.02.07"]]
  :repl-options {:init-ns async-messaging.core})
