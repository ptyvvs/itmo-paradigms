(defn operation [f] (fn [a b] (mapv f a b)))
(def v+ (operation +))
(def v- (operation -))
(def v* (operation *))
(def vd (operation /))

(defn scalar [a b] (apply + (v* a b)))
(defn vect [a b] (let [[x1 y1 z1] a [x2 y2 z2] b]
                      [(- (* y1 z2) (* y2 z1))
                       (- (* x2 z1) (* x1 z2))
                       (- (* x1 y2) (* x2 y1))
                       ]))

(defn mult [s] (partial mapv ((fn [s] (partial * s)) s)))
(defn v*s [a s] ((mult s) a))

(def m+ (operation v+))
(def m- (operation v-))
(def m* (operation v*))
(def md (operation vd))

(defn transpose [m] (apply mapv vector m))

(defn fun [a] (fn [b] (scalar a b)))
(defn mop [f] (fn [a s] (mapv (f s) a)))
(def m*s (mop mult))
(def m*v (mop fun))
(defn m*m [a b] (transpose (mapv ((fn [m] (partial m*v m)) a) (transpose b))))

(def c+  (operation m+))
(def c- (operation m-))
(def c*  (operation m*))
(def cd  (operation md))