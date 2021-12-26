(ns mire.minotaur
	(:require [Rooms :as rooms]
			  [mire.player :as player]))

(def ^:dynamic *current-room*)

(defn- move-between-refs
  "Move one instance of obj between from and to. Must call in a transaction."
  [obj from to]
  (alter from disj obj)
  (alter to conj obj))

(defn randMove
	(let [direct (rand-int 4)]
		(if (= direct 0)
		(try (move north))
		(if (= direct 1)
		(try (move east))
		(if (= direct 2)
		(try (move south))
		(try (move west)))))))

(defn move
  [direction]
  (dosync
   (let [target-name ((:exits @minotaur/*current-room*) (keyword direction))
         target (@rooms/rooms target-name)]
     (if target
       (do
		 (let [victim ((:inhabitants target))])
		 (ref-set victim/*health* 0)
         (move-between-refs minotaur
                            (:inhabitants @minotaur/*current-room*)
                            (:inhabitants target))
         (ref-set minotaur/*current-room* target) 
       )))))



	
	


	