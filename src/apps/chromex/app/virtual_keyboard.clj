(ns chromex.app.virtual-keyboard
  "The chrome.virtualKeybaord API is a kiosk only API used to
   configure virtual keyboard layout and behavior in kiosk sessions.

     * available since Chrome 58
     * https://developer.chrome.com/apps/virtualKeyboard"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro restrict-features
  "Sets restrictions on features provided by the virtual keyboard.

     |restrictions| - the preferences to enabled/disabled virtual keyboard features.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [update] where:

     |update| - https://developer.chrome.com/apps/virtualKeyboard#property-callback-update.

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/virtualKeyboard#method-restrictFeatures."
  ([restrictions] (gen-call :function ::restrict-features &form restrictions)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.app.virtual-keyboard namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.virtualKeyboard",
   :since "58",
   :functions
   [{:id ::restrict-features,
     :name "restrictFeatures",
     :callback? true,
     :params
     [{:name "restrictions", :type "virtualKeyboard.FeatureRestrictions"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "update", :type "virtualKeyboard.FeatureRestrictions"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))