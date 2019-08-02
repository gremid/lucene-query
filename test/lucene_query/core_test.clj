(ns lucene-query.core-test
  (:require [clojure.test :refer :all]
            [lucene-query.core :as lucene]
            [midje.sweet :as midje]))

(deftest roundtrip
  (let [str->ast->str (comp lucene/ast->str lucene/str->ast)
        roundtrip #(midje/fact (str->ast->str %) => %)]
    (midje/facts
     (roundtrip "quelle:a/b")
     (roundtrip "-*:test OR +test2")
     (roundtrip "!test OR test3")
     (roundtrip "!test OR [* TO 2019-01-01]")
     (roundtrip "/te\\/st/")
     (roundtrip "\"test \\\" 1\"~2")
     (roundtrip "autor:me^10 OR def:\"*gu\\*t*\"")
     (roundtrip "autor:test AND form:*te")
     (roundtrip "status:(Red-f OR Red-2 OR Artikelrump*)")
     (roundtrip "status:(Red-f OR Red-2 OR Artikelrump*) AND !autor:me"))))
