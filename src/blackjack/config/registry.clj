(ns blackjack.config.registry
  (:require [blackjack.util.shared :as shared])
  (:use [blackjack.infrastructure.adapter.driving.gamerepository.in-memory])
  (:use [blackjack.infrastructure.adapter.driving.playerrepository.in-memory])
  (:use [blackjack.infrastructure.adapter.driving.tablerepository.in-memory])
  (:use [blackjack.infrastructure.adapter.driving.eventbus.cometd-bus])
  )

(def player-repository (->InMemoryPlayerRepository))
(def table-repository (->InMemoryTableRepository))
(def game-repository (->InMemoryGameRepository))
(def external-event-bus (->CometDEventBus))