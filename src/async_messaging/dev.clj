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
  
  
  )
