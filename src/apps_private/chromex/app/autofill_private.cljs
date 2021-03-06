(ns chromex.app.autofill-private (:require-macros [chromex.app.autofill-private :refer [gen-wrap]])
    (:require [chromex.core]))

; -- functions --------------------------------------------------------------------------------------------------------------

(defn save-address* [config address]
  (gen-wrap :function ::save-address config address))

(defn get-country-list* [config]
  (gen-wrap :function ::get-country-list config))

(defn get-address-components* [config country-code]
  (gen-wrap :function ::get-address-components config country-code))

(defn get-address-list* [config]
  (gen-wrap :function ::get-address-list config))

(defn save-credit-card* [config card]
  (gen-wrap :function ::save-credit-card config card))

(defn remove-entry* [config guid]
  (gen-wrap :function ::remove-entry config guid))

(defn validate-phone-numbers* [config params]
  (gen-wrap :function ::validate-phone-numbers config params))

(defn get-credit-card-list* [config]
  (gen-wrap :function ::get-credit-card-list config))

(defn mask-credit-card* [config guid]
  (gen-wrap :function ::mask-credit-card config guid))

(defn migrate-credit-cards* [config]
  (gen-wrap :function ::migrate-credit-cards config))

; -- events -----------------------------------------------------------------------------------------------------------------

(defn on-address-list-changed* [config channel & args]
  (gen-wrap :event ::on-address-list-changed config channel args))

(defn on-credit-card-list-changed* [config channel & args]
  (gen-wrap :event ::on-credit-card-list-changed config channel args))

