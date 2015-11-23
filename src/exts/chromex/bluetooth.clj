(ns chromex.bluetooth
  "Use the chrome.bluetooth API to connect to a Bluetooth
   device. All functions report failures via chrome.runtime.lastError.
   
     * available since Chrome 24
     * https://developer.chrome.com/extensions/bluetooth"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro get-adapter-state
  "Get information about the Bluetooth adapter.
   
     |callback| - Called with an AdapterState object describing the adapter              state."
  ([#_callback] (gen-call :function ::get-adapter-state (meta &form))))

(defmacro get-device
  "Get information about a Bluetooth device known to the system.
   
     |deviceAddress| - Address of device to get.
     |callback| - Called with the Device object describing the device."
  ([device-address #_callback] (gen-call :function ::get-device (meta &form) device-address)))

(defmacro get-devices
  "Get a list of Bluetooth devices known to the system, including paired and recently discovered devices.
   
     |callback| - Called when the search is completed."
  ([#_callback] (gen-call :function ::get-devices (meta &form))))

(defmacro start-discovery
  "Start discovery. Newly discovered devices will be returned via the onDeviceAdded event. Previously discovered
   devices already known to the adapter must be obtained using getDevices and will only be updated using the
   |onDeviceChanged| event if information about them changes.Discovery will fail to start if this application has
   already called startDiscovery.  Discovery can be resource intensive: stopDiscovery should be called as soon as
   possible.
   
     |callback| - Called to indicate success or failure."
  ([#_callback] (gen-call :function ::start-discovery (meta &form))))

(defmacro stop-discovery
  "Stop discovery.
   
     |callback| - Called to indicate success or failure."
  ([#_callback] (gen-call :function ::stop-discovery (meta &form))))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-adapter-state-changed-events
  "Fired when the state of the Bluetooth adapter changes."
  [channel]
  (gen-call :event ::on-adapter-state-changed (meta &form) channel))

(defmacro tap-on-device-added-events
  "Fired when information about a new Bluetooth device is available."
  [channel]
  (gen-call :event ::on-device-added (meta &form) channel))

(defmacro tap-on-device-changed-events
  "Fired when information about a known Bluetooth device has changed."
  [channel]
  (gen-call :event ::on-device-changed (meta &form) channel))

(defmacro tap-on-device-removed-events
  "Fired when a Bluetooth device that was previously discovered has been out of range for long enough to be considered
   unavailable again, and when a paired device is removed."
  [channel]
  (gen-call :event ::on-device-removed (meta &form) channel))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.bluetooth",
   :since "24",
   :functions
   [{:id ::get-adapter-state,
     :name "getAdapterState",
     :since "25",
     :callback? true,
     :params
     [{:name "callback",
       :type :callback,
       :callback {:params [{:name "adapter-info", :type "bluetooth.AdapterState"}]}}]}
    {:id ::get-device,
     :name "getDevice",
     :since "35",
     :callback? true,
     :params
     [{:name "device-address", :type "string"}
      {:name "callback",
       :type :callback,
       :callback {:params [{:name "device-info", :type "bluetooth.Device"}]}}]}
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
     :since "25",
     :params [{:name "state", :type "bluetooth.AdapterState"}]}
    {:id ::on-device-added,
     :name "onDeviceAdded",
     :since "35",
     :params [{:name "device", :type "bluetooth.Device"}]}
    {:id ::on-device-changed,
     :name "onDeviceChanged",
     :since "35",
     :params [{:name "device", :type "bluetooth.Device"}]}
    {:id ::on-device-removed,
     :name "onDeviceRemoved",
     :since "35",
     :params [{:name "device", :type "bluetooth.Device"}]}]})

; -- helpers --------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))