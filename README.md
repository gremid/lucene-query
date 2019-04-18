# lucene-query

A parser for Apache Lucene Classic Queries.

[![Clojars Project](https://img.shields.io/clojars/v/gremid/lucene-query.svg)](https://clojars.org/gremid/lucene-query)

## Usage

```clojure
(require '[lucene-query.core :as lucene])
(lucene/str->ast "field1:val AND -field2:val") ;; => [:query [:clause [:field [:term "field1"]] [:value [:term "val"]]] [:and] [:clause [:must-not] [:field [:term "field2"]] [:value [:term "val"]]]]
```
    
## License

Copyright &copy; 2019 Gregor Middell

This project is licensed under the GNU Lesser General Public License v3.0.
