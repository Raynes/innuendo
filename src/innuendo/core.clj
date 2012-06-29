(ns innuendo.core
  (:require [clj-http.client :as http]))

(defn read-clojure [data]
  (binding [*read-eval* false]
    (read-string data)))

(defn process-response [{:keys [status] :as resp}]
  (cond
   (#{404 400 422} status) (assoc resp :body (read-clojure (:body resp)))
   (#{201 200} status) (read-clojure (:body resp))
   (= 204 status) true
   :else resp))

(defn req [method endpoint positional optional]
  (process-response
   (http/request
    (merge {:method method
            :url (str "https://www.refheap.com/api/"
                      (apply format endpoint positional))
            :throw-exceptions false}
           {(if (= method :post) :form-params :query-params)
            (assoc optional :return "clojure")}))))

(defn create-paste
  "Create a new paste.
   Options are:
      username -- Optional. If this and a token is not present, paste will be anonymous.
      token    -- Optional.
      contents -- Required. Cannot be empty.
      private  -- Optional. true or false. Default is false.
      language -- Optional. Must be in exactly the same format as it appears in the dropdown
                  on http://refheap.com/paste. If not provided, will default to Plain Text."
  [contents & [options]]
  (req :post "paste" [] (assoc options :contents contents)))

(defn edit-paste
  "Edit an existing paste. If any parameters listed as optional are not provided, they will default
   to what the old paste had.
   Options are:
      username -- Required.
      token    -- Required.
      contents -- Optional. Cannot be empty.
      private  -- Optional. true or false.
      language -- Optional. Must be in exactly the same format as it appears in the dropdown
                  on http://refheap.com/paste."
  [id options]
  (req :post "paste/%s" [id] options))

(defn fork-paste
  "Fork an existing paste to your account.
   Options are:
      username -- Required.
      token    -- Required."
  [id options]
  (req :post "paste/%s/fork" [id] options))

(defn get-paste
  "Get an existing paste."
  [id]
  (req :get "paste/%s" [id] nil))

(defn delete-paste
  "Delete a paste.
   Options are:
      username -- Required.
      token    -- Required."
  [id options]
  (req :delete "paste/%s" [id] options))
