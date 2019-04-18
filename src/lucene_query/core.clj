(ns lucene-query.core
  (:require [clojure.java.io :as io]
            [instaparse.core :as insta :refer [defparser transform]]
            [clojure.string :as str]))

(defn escape [char-re s] (str/replace s char-re #(str "\\" %)))

(defn unescape [char-re]
  (let [re (re-pattern (str "\\\\" char-re))]
    (fn [s] (str/replace s re #(subs % 1 2)))))

(def escape-quoted (partial escape #"\""))
(def unescape-quoted (unescape "\""))

(def escape-regexp (partial escape #"/"))
(def unescape-regexp (unescape "/"))

(def escape-pattern (partial escape #"[\!\(\)\:\^\[\]\"\{\}\~\\]"))
(def unescape-pattern (unescape "[\\!\\(\\)\\:\\^\\[\\]\\\"\\{\\}\\~\\\\]"))

(def escape-term (comp (partial escape #"[\*\?]") escape-pattern))
(def unescape-term (comp unescape-pattern (unescape "[\\*\\?]")))

(defparser parse-str (io/resource "query.bnf"))

(defn- str-node
  ([type] (str-node type identity))
  ([type unescape-fn] (fn [& args] [type (->> args (apply str) unescape-fn)])))

(def ast->ast (partial transform {:term (str-node :term unescape-term)
                                  :pattern (str-node :pattern unescape-pattern)
                                  :regexp (str-node :regexp unescape-regexp)
                                  :quoted (str-node :quoted unescape-quoted)
                                  :fuzzy (str-node :fuzzy)
                                  :boost (str-node :boost)}))

(defn error->ex [r] (if (vector? r) r (throw (ex-info "Parser failed" r))))

(def str->ast (comp error->ex ast->ast parse-str))

(defn ast->str [[type & [arg :as args]]]
  (condp = type
    :sub-query (str "(" (apply str (map ast->str args)) ")")
    :range (let [[lp lo up rp] args]
             (str lp (ast->str lo) " TO " (ast->str up) rp))

    :field (str (apply str (map ast->str args)) ":")
    :boost (str "^" arg)
    :fuzzy (str "~" arg)

    :and " AND "
    :or " OR "
    :not "!"
    :must "+"
    :must-not "-"
    :all "*"

    :term (escape-term arg)
    :pattern (escape-pattern arg)
    :quoted (str "\"" (escape-quoted arg) "\"")
    :regexp (str "/" (escape-regexp arg) "/")

    (apply str (map ast->str args))))
