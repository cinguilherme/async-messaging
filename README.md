> [![Clojars Project](https://img.shields.io/clojars/v/org.clojars.cinguilherme/async-messaging.svg)](https://clojars.org/org.clojars.cinguilherme/async-messaging)


> ![Clojars Project](https://img.shields.io/clojars/v/org.clojars.cinguilherme/async-messaging.svg?include_prereleases)

Lein
```clojure
[org.clojars.cinguilherme/async-messaging "0.0.5-SNAPSHOT"]
```

deps.edn
```clojure
org.clojars.cinguilherme/async-messaging {:mvn/version "0.0.5-SNAPSHOT"}
```

# async-messaging

async-messaging is a Clojure library that abstracts messaging systems by providing a unified producer and consumer protocol. It simplifies integration with different messaging systems while keeping application code agnostic to the underlying implementation.

All components are designed to work with the [Component](https://github.com/stuartsierra/component) library, allowing them to be managed within a system map.

## Why?

Messaging frameworks often introduce rigid APIs and assumptions. While this can be useful, it also makes switching between systems difficult and forces applications to conform to a specific design.

async-messaging is built to:
- **Reduce vendor lock-in** by offering an **unopinionated** API.
- **Allow switching messaging backends** without modifying application logic.
- **Standardize message production and consumption** while still allowing system-specific configurations when needed.

## Design Decisions

The library defines two independent protocols:

- **Producer:** Sends messages to a messaging system.
- **Consumer:** Receives and processes messages.

### Consumer Design

The **consumer's handler function should be completely interchangeable** across different messaging systems. The library ensures that the way you **process** a message does not depend on the messaging backend.

However, messaging systems differ in how they handle:
- **Dead-letter queues (DLQs):** Some systems support automatic DLQs (e.g., SQS, RabbitMQ), while others require custom handling.
- **Retries & failure handling:** Some systems offer built-in retry mechanisms, while others need a manual requeue strategy.
- **Message ordering guarantees:** Kafka provides ordered delivery, while RabbitMQ and SQS do not guarantee strict ordering.

async-messaging abstracts the **core** consumer behavior, but system-specific features (like dead-letter handling) **may need explicit configuration**.

### Producer Design

The producer protocol is more complex due to system differences in:
- **Destinations:** Topics vs. queues vs. event buses.
- **Routing & filtering:** Systems like RabbitMQ allow custom exchange types, while SQS has no equivalent.
- **Message scheduling & delays:** Only a few systems (e.g., SQS, RabbitMQ with plugins) allow scheduled or delayed delivery.
- **Message expiration (TTL):** Some systems automatically drop old messages, while others retain them indefinitely.

async-messaging provides a **common API** for sending messages, but system-specific options (e.g., delayed messages, retries, DLQs) require explicit configuration per messaging backend.

## Usage

Just like any other component, you can use the `start` and `stop` functions to start and stop the components.

```clojure

(require '[async-messaging.core :as am])

(def system
  (-> (component/system-map
       :producer (am/start-producer {:type :kafka :config {:bootstrap.servers "localhost:9092"}})
       :consumer (am/start-consumer {:type :kafka :config {:bootstrap.servers "localhost:9092"}}))
      (component/start)))

;; do something with the system

(component/stop system)
```

There is a lot of hacking done in `dev.clj` to test the undeling libs and the components. You can use it as a reference to see how to use the components.


### Supported Systems, so far
Supported but does not mean that it is fully implemented or tested or even working as expected.
Look at the version number for this lib, this is far form ready to be used in production.

- [ ] Kafka
- [x] RabbitMQ
- [ ] ActiveMQ
- [ ] Google PubSub
- [ ] Amazon SQS
- [x] Redis
- [x] In Memory EventBus (mainly for testing enviroments where not even a docker container is allowed)
- [ ] NATS streaming


## Testing Considerations

async-messaging is designed with **testability** in mind. All components—producers and consumers—can be configured to allow side effects to be **recuperable**, making it possible to inspect what was sent and received.

### Why is this important?

In real-world applications, messaging systems are often involved in complex workflows. Consider this example:

1. A **POST** request triggers a database write.
2. The system **produces a message** into a queue.
3. A consumer **processes** the message and updates another database table.

While traditional tests might only assert the final database state, **being able to verify messaging behavior directly** is crucial for:
- Ensuring that the **producer actually produced messages**.
- Checking that the **consumer processed messages in the expected order**.
- Validating that **dead-letter messages were properly handled**.

### Built-in Test Helpers

Consumers provide an interface to inspect consumed messages and dead-letter queues:

```clojure
;; Retrieve consumed messages and dead letters during a test
(consumer/delta)
;; => {:consumed-messages [...] :deadletters [...]}
```

## License

This project is licensed under the MIT License. See the [LICENSE](https://opensource.org/licenses/MIT) file for details.

ThiCopyright (c) [2025], [Cintra, Guilherme]

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.