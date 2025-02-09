[![Clojars Project](https://img.shields.io/clojars/v/org.clojars.cinguilherme/async-messaging.svg)](https://clojars.org/org.clojars.cinguilherme/async-messaging)

[![Clojars Project](https://img.shields.io/clojars/v/org.clojars.cinguilherme/async-messaging.svg?include_prereleases)]


# async-messaging

A Clojure library designed to abstract alot about messaging systems, providing a simple and easy to use protocol and compoenents to work with messaging systems.

All the components are designed to be used within system maps with the [Component](https://github.com/stuartsierra/component) library.

The library provides two independend protocols, producer and consumer. 
The producer protocol is used to send messages to a messaging system, while the consumer protocol is used to receive messages from a messaging system.

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


## License

This project is licensed under the MIT License. See the [LICENSE](https://opensource.org/licenses/MIT) file for details.

ThiCopyright (c) [2025], [Cintra, Guilherme]

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.