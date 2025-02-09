(ns async-messaging.dev
  (:require [taoensso.carmine :as car :refer [wcar]]
            [taoensso.carmine.message-queue :as car-mq]
            [com.stuartsierra.component :as component]
            [async-messaging.utils :refer [tapc]]

            ;; rabitMQ
            [langohr.core      :as rmq]
            [langohr.channel   :as lch]
            [langohr.queue     :as lq]
            [langohr.consumers :as lc]
            [langohr.basic     :as lb]
            ;; end rabbit

            [async-messaging.protocols :as proto]
            [async-messaging.in-mem-event-bus.core :as core]
            [async-messaging.in-mem-event-bus.component :as c-bus]
            ))

(comment
  (def bus  (atom {}))
  (def test? true)

  (def event-map {:queue "test-queue"})

  (def producer (component/start (c-bus/create-in-mem-producer event-map bus test?)))

  (println producer)

  (proto/send-message producer {:destination {:queue "test-queue"} :message "test-message"} {})
  
  )
