(ns async-messaging.redis.component
  (:require [com.stuartsierra.component :as component]
            [async-messaging.protocols :as proto]
            [taoensso.carmine :as car :refer [wcar]]
            [taoensso.carmine.message-queue :as car-mq]
            [clojure.pprint :as pprint]))

(defn tap [v]
  (pprint/pprint  v)
  v)

;- :min-idle-per-key  ; Min num of idle conns to keep per sub-pool (Default 0)
;- :max-idle-per-key  ; Max num of idle conns to keep per sub-pool (Default 16)
;- :max-total-per-key ; Max num of idle or active conns <...>      (Default 16)
(def poll-configs {})
(def default-producer-ops {:async? false})

(defrecord RedisProducer [server-info pool-settings events-map consumer-map]
  component/Lifecycle
  (start [this]
    (let [conn-spec {:uri (-> server-info :uri)}
          conn-pool (car/connection-pool pool-settings)
          wcar-opts {:pool conn-pool :spec conn-spec}]
      (assoc this :redis-producer {:wcar wcar-opts})))
  (stop [this]
    (dissoc this :redis-producer))

  proto/CommonProducer
  (send-message [this message ops]
    (let [opsx (tap (merge default-producer-ops ops))
          wcar-opts (-> this :redis-producer :wcar)
          payload (-> message :message)
          destination (-> message :destination)
          async? (-> opsx :async?)]
      (if async?
        (future (car/wcar wcar-opts (car-mq/enqueue (:queue destination) payload)))
        (car/wcar wcar-opts (car-mq/enqueue (:queue destination) payload)))))
  (send-messages [this messages ops]
    (let [opsx (merge default-producer-ops ops)
          async? (-> opsx :async?)]
      (mapv (fn [message]
              (let [wcar-opts (-> this :redis-producer :wcar)
                    payload (-> message :message)
                    destination (-> message :destination)]
                (if async?
                  (future (car/wcar wcar-opts (car-mq/enqueue (:queue destination) payload)))
                  (car/wcar wcar-opts (car-mq/enqueue (:queue destination) payload))))) messages))))

(defn create-redis-producer 
  ([server-info pool-settings]
   (->RedisProducer server-info pool-settings {} {}))
  ([server-info pool-settings events-map consumer-map]
   (->RedisProducer server-info pool-settings events-map consumer-map)))

(declare start-list)
(defn start-list [list wcar-opts]
  (mapv (fn [settings]
          (println "starting consumer for settings" settings)
          (let [queue (-> settings :queue)
                callback (-> settings :handler)
                error-callback (-> settings :error-callback)]
            (car-mq/worker wcar-opts queue
                           {:handler callback
                            :error-callback error-callback})))
        list))

(defrecord RedisConsumer [server-info pool-settings events-map consumer-map]
  component/Lifecycle
  (start [this]
    (let [conn-spec {:uri (-> server-info :uri)}
          conn-pool (car/connection-pool pool-settings)
          wcar-opts {:pool conn-pool :spec conn-spec}] 
      (println "Starting Redis Consumer")
      (assoc this :redis-consumer {:wcar wcar-opts})))
  (stop [this]
    (println "Stopping Redis Consumer")
    ;; before dissoc the redis-consumer we need to stop all the consumers listeners in :redis-consumer :consumers
    (doseq [worker (-> this :redis-consumer :consumers)]
      (deref worker)
      (worker :stop))
    (dissoc this :redis-consumer))

  proto/CommonConsumer
  (listen
    [this settings] ;; settings will be {:consumer-x {:handler s/fn :error-callback s/fn :queue s/str}}
    (let [wcar-opts (-> this :redis-consumer :wcar)
          list (-> settings vals)
          consumers-list (start-list list wcar-opts)
          redis-consumer (:redis-consumer this)
          with-consumers (assoc redis-consumer :consumers consumers-list)]
      (assoc this :redis-consumer with-consumers))))

(defn create-redis-consumer 
  ([server-info pool-settings]
   (->RedisConsumer server-info pool-settings {} {}))
  ([server-info pool-settings events-map consumer-map]
   (->RedisConsumer server-info pool-settings events-map consumer-map)))
