(defproject gremid/lucene-query "0.1.0-SNAPSHOT"

  :description "Parser for Apache Lucene Classic Queries"
  :url "https://github.com/gremid/lucene-query"

  :license {:name "GNU Lesser General Public License v3.0"
            :url "https://www.gnu.org/licenses/lgpl-3.0"
            :year 2019
            :key "lgpl-3.0"}

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [instaparse "1.4.10"]]

  :deploy-repositories [["releases" :clojars]
                        ["snapshots" :clojars]]

  :repl-options {:init-ns lucene-query.core})
