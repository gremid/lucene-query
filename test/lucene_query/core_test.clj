(ns lucene-query.core-test
  (:require [clojure.test :refer :all]
            [lucene-query.core :as lq]
            [midje.sweet :as midje]))

(deftest roundtrip
  (let [str->ast->str (comp lq/ast->str lq/str->ast)
        rt (fn [q] (midje/fact (str->ast->str q) => q))]
    (midje/facts
     (rt "-*:test OR +test2")
     (rt "!test OR test3")
     (rt "!test OR [* TO 2019-01-01]")
     (rt "/te\\/st/")
     (rt "\"test \\\" 1\"~2")
     (rt "autor:me^10 OR def:\"*gu\\*t*\"")
     (rt "autor:test AND form:*te")
     (rt "status:(Red-f OR Red-2 OR Artikelrump*)")
     (rt "status:(Red-f OR Red-2 OR Artikelrump*) AND !autor:me"))))
