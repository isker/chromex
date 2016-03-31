(ns chromex.app.bluetooth
  "Use the chrome.bluetooth API to connect to a Bluetooth
   device. All functions report failures via chrome.runtime.lastError.

     * available since Chrome 37
     * https://developer.chrome.com/apps/bluetooth"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex.wrapgen :refer [gen-wrap-from-table]]
            [chromex.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro get-adapter-state
  "Get information about the Bluetooth adapter.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [adapter-info] where:

     |adapter-info| - Object containing the adapter information.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetooth#method-getAdapterState."
  ([] (gen-call :function ::get-adapter-state &form)))

(defmacro get-device
  "Get information about a Bluetooth device known to the system.

     |device-address| - Address of device to get.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [device-info] where:

     |device-info| - Object containing the device information.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetooth#method-getDevice."
  ([device-address] (gen-call :function ::get-device &form device-address)))

(defmacro get-devices
  "Get a list of Bluetooth devices known to the system, including paired and recently discovered devices.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [device-infos] where:

     |device-infos| - Array of object containing device information.

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetooth#method-getDevices."
  ([] (gen-call :function ::get-devices &form)))

(defmacro start-discovery
  "Start discovery. Newly discovered devices will be returned via the onDeviceAdded event. Previously discovered devices
   already known to the adapter must be obtained using getDevices and will only be updated using the |onDeviceChanged| event
   if information about them changes.Discovery will fail to start if this application has already called startDiscovery.
   Discovery can be resource intensive: stopDiscovery should be called as soon as possible.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetooth#method-startDiscovery."
  ([] (gen-call :function ::start-discovery &form)))

(defmacro stop-discovery
  "Stop discovery.

   This function returns a core.async channel which eventually receives a result value and closes.
   Signature of the result value put on the channel is [].

   In case of error the channel closes without receiving any result and relevant error object can be obtained via
   chromex.error/get-last-error.

   https://developer.chrome.com/apps/bluetooth#method-stopDiscovery."
  ([] (gen-call :function ::stop-discovery &form)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-adapter-state-changed-events
  "Fired when the state of the Bluetooth adapter changes.

   Events will be put on the |channel| with signature [::on-adapter-state-changed [state]] where:

     |state| - The new state of the adapter.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/bluetooth#event-onAdapterStateChanged."
  ([channel & args] (apply gen-call :event ::on-adapter-state-changed &form channel args)))

(defmacro tap-on-device-added-events
  "Fired when information about a new Bluetooth device is available.

   Events will be put on the |channel| with signature [::on-device-added [device]] where:

     |device| - https://developer.chrome.com/apps/bluetooth#property-onDeviceAdded-device.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/bluetooth#event-onDeviceAdded."
  ([channel & args] (apply gen-call :event ::on-device-added &form channel args)))

(defmacro tap-on-device-changed-events
  "Fired when information about a known Bluetooth device has changed.

   Events will be put on the |channel| with signature [::on-device-changed [device]] where:

     |device| - https://developer.chrome.com/apps/bluetooth#property-onDeviceChanged-device.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/bluetooth#event-onDeviceChanged."
  ([channel & args] (apply gen-call :event ::on-device-changed &form channel args)))

(defmacro tap-on-device-removed-events
  "Fired when a Bluetooth device that was previously discovered has been out of range for long enough to be considered
   unavailable again, and when a paired device is removed.

   Events will be put on the |channel| with signature [::on-device-removed [device]] where:

     |device| - https://developer.chrome.com/apps/bluetooth#property-onDeviceRemoved-device.

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call.

   https://developer.chrome.com/apps/bluetooth#event-onDeviceRemoved."
  ([channel & args] (apply gen-call :event ::on-device-removed &form channel args)))

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
  {:namespace "chrome.bluetooth",
   :since "37",
   :functions
   [{:id ::get-adapter-state,
     :name "getAdapterState",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "adapter-info", :type "bluetooth.AdapterState"}]}}]}
    {:id ::get-device,
     :name "getDevice",
     :callback? true,
     :params
     [{:name "device-address", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "device-info", :type "bluetooth.Device"}]}}]}
    {:id ::get-devices,
     :name "getDevices",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "device-infos", :type "[array-of-bluetooth.Devices]"}]}}]}
    {:id ::start-discovery,
     :name "startDiscovery",
     :callback? true,
     :params [{:name "callback", :optional? true, :type :callback}]}
    {:id ::stop-discovery,
     :name "stopDiscovery",
     :callback? true,
     :params [{:name "callback", :optional? true, :type :callback}]}],
   :events
   [{:id ::on-adapter-state-changed,
     :name "onAdapterStateChanged",
     :params [{:name "state", :type "bluetooth.AdapterState"}]}
    {:id ::on-device-added, :name "onDeviceAdded", :params [{:name "device", :type "bluetooth.Device"}]}
    {:id ::on-device-changed, :name "onDeviceChanged", :params [{:name "device", :type "bluetooth.Device"}]}
    {:id ::on-device-removed, :name "onDeviceRemoved", :params [{:name "device", :type "bluetooth.Device"}]}]})

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