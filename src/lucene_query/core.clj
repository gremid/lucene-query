(ns lucene-query.core
  (:require [clojure.java.io :as io]
            [instaparse.core :as insta :refer [defparser]]))

(defparser query-parser
  (io/resource "query.bnf"))

(defn strcat [type]
  (fn [& args]
    (if (every? string? args)
      [type (apply str args)]
      (vec (concat [type] args)))))

(def str-nodes [:field :term :pattern :regexp :quoted :fuzzy :boost :bound])
(def transforms (into {:all (constantly :all)}
                      (map #(vector % (strcat %)) str-nodes)))
(def parse (comp (partial insta/transform transforms) query-parser))

;;(time (parse "(-text_ss:/test/^1.0 OR *:[2019-10-01 TO *} OR autor:(neu* OR \"alt\\\"\"))^3.0 AND +filter:true~10"))
