(defproject net.middell/lucene-query "0.1.0-SNAPSHOT"

  :description "Parser for Apache Lucene Classic Queries"
  :url "https://github.com/gremid/lucene-query"

  :license {:name "GNU Lesser General Public License v3.0"
            :url "https://www.gnu.org/licenses/lgpl-3.0"
            :year 2019
            :key "lgpl-3.0"}

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [instaparse "1.4.10"]]

  :scm {:url "git@github.com:gremid/lucen-query.git"}

  :pom-addition [:developers
                 [:developer {:id "gremid"}
                  [:name "Gregor Middell"]
                  [:email "gregor@middell.net"]
                  [:role "Developer"]
                  [:timezone "Europe/Berlin"]]]

  :deploy-repositories
  [["releases" {:url "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
                :creds :gpg}
    "snapshots" {:url "https://oss.sonatype.org/content/repositories/snapshots/"
                 :creds :gpg}]]

  :repl-options {:init-ns lucene-query.core})
