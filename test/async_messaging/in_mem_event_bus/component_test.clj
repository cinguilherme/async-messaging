(ns async-messaging.in-mem-event-bus.component-test
  (:require [clojure.test :refer [deftest is]]
            [async-messaging.utils :refer [not-nil?]]
            [async-messaging.in-mem-event-bus.component :as c-bus]
            [async-messaging.protocols :as proto]
            [com.stuartsierra.component :as component]))

(deftest test-producer-test
  (let [bus (atom {})
        test? true
        producer (component/start (c-bus/create-in-mem-producer {} bus test?))
        message {:destination {:queue "test-queue"} :message "test-message"}
        message2 {:destination {:queue "test-queue-2"} :message "test-message-2"}
        ops {}]
    (proto/send-message producer message ops)
    (proto/send-message producer message2 ops)
    (is (not-nil? @(:side-effects producer)))
    (is (= @(:side-effects producer) {:test-queue ["test-message"]
                                      :test-queue-2 ["test-message-2"]}))))

(deftest prod-producer-test
  (let [bus (atom {})
        test? false
        producer (component/start (c-bus/create-in-mem-producer {} bus test?))
        message {:destination {:queue "test-queue"} :message "test-message"}
        ops {}]
    (proto/send-message producer message ops)
    (is (nil? (:side-effects producer)))))
