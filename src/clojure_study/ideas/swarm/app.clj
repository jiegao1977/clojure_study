(ns
  ^{:author mate.magyari}
  clojure-study.ideas.swarm.app
  (:require [clojure.test :as test]
            [clojure-study.ideas.swarm.swarm :as swarm]
            [clojure_study.ideas.swarm.the-wild :as w]
            [clojure-study.ideas.swarm.display :as display]
            [clojure-study.ideas.swarm.view-adapter :as v]))

(def global-constants
  {:gravity-constants {:sheep {:sheep 1
                               :wolf -3
                               :wall -20}
                       :wolf {:sheep 3
                              :wolf 1
                              :wall -20}
                       :wall {:sheep 0
                              :wolf 0
                              :wall 0}}
   :min-proximity 1.0})

(def entity-template
  {:position nil
   :speed 3
   :stray (/ Math/PI 32) ; defines the angle the entity can deviate from the gravitational vector
   :g-map nil
   :type nil})

(defn rand-factor-creator
  "Produces a psesudo-random number generator function"
  []
  (let [last (atom Math/PI)]
    (fn []
      (let [n1 (* 13 @last)
            n2 (- n1 (int n1))]
        (reset! last n2)
        n2))))

(def rand-factor (rand-factor-creator))
;(def rand-factor rand)

(defn run-show!
  "Runs the simulation"
  [sheeps-num wolves-num dim-board dim-screen]
  (let [wolf-traits {:type :wolf}
        sheep-traits {:type :sheep}
        sheeps (w/enitites-of-type sheeps-num sheep-traits entity-template global-constants dim-board rand-factor)
        wolves (w/enitites-of-type wolves-num wolf-traits entity-template global-constants dim-board rand-factor)
        walls (w/make-walls dim-board)
        repaint! (display/repaint-fn dim-board dim-screen)]
    (println "START")
    (loop [i 500
           entities (concat sheeps wolves walls)]
      (if (zero? i)
        (println "END")
        (let [entitites-next (swarm/next-positions entities global-constants rand-factor)]
          ;(println "hi " i " " (apply va/weight-point (map :position entitites-next)))
          (repaint! (v/entities->view entitites-next))
          (recur (dec i) entitites-next))))))

(run-show! 100 10 [200 200] [400 400])
