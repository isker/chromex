(ns chromex.version)

(def current-version "0.5.6-SNAPSHOT")                                                                                        ; this should match our project.clj

(defmacro get-current-version []
  current-version)
