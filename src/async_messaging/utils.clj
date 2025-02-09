(ns async-messaging.utils
  (:require [clojure.string :as string]
            [cheshire.core :as json]))

(def ansi-reset "\u001B[0m")
(def ansi-green "\u001B[32m")

(defn print-json-colored
  "Prints data as a pretty JSON string in green."
  [v]
  (let [json-str (json/generate-string v {:pretty true})
        lines (string/split-lines json-str)]
    (doseq [line lines]
      (println (str ansi-green line ansi-reset)))
    v))

(defn not-nil? [x] (not (nil? x)))

(defmacro tapc
  "Evaluates `x`, prints the result in JSON format with colors, then returns it."
  [x]
  `(let [result# ~x]
     (print-json-colored result#)
     result#))

(comment
  (tapc {:a 1 "s" "some tring" :id (random-uuid)})

  (tapc {:a      1
         "s"     "some string"
         :id     (random-uuid)
         :nested {:foo [1 2 3]
                  :bar {:x "hello" :y "world"}}})

  (tapc [{:a 1} {"s" "string"}])

  ;;
  )


