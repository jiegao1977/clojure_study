(ns blackjack.app.service.seating-app-service
  (:require [blackjack.util.shared :as s]
            [blackjack.config.registry :as r]
            [blackjack.domain.table.table :as t]
            [blackjack.app.eventbus :as e]
            [blackjack.port.table-repository :as tr]
            [blackjack.app.lockable :refer [with-lock]]))

(defn seat-player! [player-id table-id]
  (with-lock table-id r/table-repository
    (let [table (-> (tr/get-table r/table-repository table-id)
                  (t/sit player-id))
          [events,t] (s/remove-events table)]
      (when (s/seq-contains? (:players t) player-id)
        (tr/save-table! r/table-repository t))
      (e/publish-events! events))))