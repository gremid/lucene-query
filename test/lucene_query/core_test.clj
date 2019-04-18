(ns lucene-query.core-test
  (:require [lucene-query.core :as lq]
            [midje.sweet :as midje]))

(def str->ast->str (comp lq/ast->str lq/str->ast))

(defn roundtrip [q] (midje/fact str->ast->str q => q))

(midje/facts
 "about str->ast->str"
 (roundtrip "-test OR +test2")
 (roundtrip "!test OR test3")
 (roundtrip "!test OR [* TO 2019-01-01]")
 (roundtrip "/te\\/st/")
 (roundtrip "\"test \\\" 1\"~2")
 (roundtrip "autor:me^a OR def:\"*gu\\*t*\"")
 (roundtrip "autor:test AND form:*te")
 (roundtrip "status:(Red-f OR Red-2 OR Artikelrump*)")
 (roundtrip "status:(Red-f OR Red-2 OR Artikelrump*) AND !autor:me"))

