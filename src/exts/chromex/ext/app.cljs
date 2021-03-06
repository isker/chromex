(ns chromex.ext.app (:require-macros [chromex.ext.app :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn get-is-installed* [config]
  (gen-wrap :function ::get-is-installed config))

(defn install-state* [config]
  (gen-wrap :function ::install-state config))

(defn running-state* [config]
  (gen-wrap :function ::running-state config))

(defn get-details* [config]
  (gen-wrap :function ::get-details config))

