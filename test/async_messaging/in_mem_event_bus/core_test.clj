(ns async-messaging.in-mem-event-bus.core-test
  (:require [clojure.test :refer [deftest is]]
            [async-messaging.in-mem-event-bus.core :as core]
            [async-messaging.utils :refer [not-nil?]]))

(deftest test-poll-queue!
  (let [bus (atom {:test-queue clojure.lang.PersistentQueue/EMPTY})
        stop? (atom false)
        handler (fn [message] (println message))
        queue-key :test-queue
        future-ref (core/poll-queue! bus queue-key handler stop?)] 
    (reset! bus {:test-queue (conj clojure.lang.PersistentQueue/EMPTY "test-message")})
    (Thread/sleep 100)
    (reset! stop? true)
    (Thread/sleep 100)
    (is (nil? @future-ref))))

(deftest test-iterate-consumer-map-add-listeners!
  (let [bus (atom {})
        stop? (atom false)
        consumer-map {:test-queue {:queue "test-queue" :handler (fn [message] (println message))}}
        threads-atom (atom {})]
    (core/iterate-consumer-map-add-listeners! threads-atom stop? consumer-map bus)
    (reset! bus {:test-queue (conj clojure.lang.PersistentQueue/EMPTY "test-message")})
    (Thread/sleep 100)
    (reset! stop? true)
    (Thread/sleep 100)
    (is (not-nil? @threads-atom))))