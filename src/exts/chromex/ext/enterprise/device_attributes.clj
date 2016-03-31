(ns chromex.ext.enterprise.device-attributes
  "Use the chrome.enterprise.deviceAttributes API to read device
   attributes.

     * available since Chrome 46
     * https://developer.chrome.com/extensions/enterprise.deviceAttributes"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-directory-device-id
  "Fetches the value of the device identifier of the directory API, that is generated by the server and identifies the cloud
   record of the device for querying in the cloud directory API.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [device-id] where:

     |device-id| - https://developer.chrome.com/extensions/enterprise.deviceAttributes#property-callback-deviceId.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/extensions/enterprise.deviceAttributes#method-getDirectoryDeviceId."
  ([] (gen-call :function ::get-directory-device-id &form)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in this namespace."
  [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.enterprise.deviceAttributes",
   :since "46",
   :functions
   [{:id ::get-directory-device-id,
     :name "getDirectoryDeviceId",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "device-id", :type "string"}]}}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))