(defn s [op]
  (fn [s1 s2]
    (cond
      (number? s1) (op s1 s2)
      :else (mapv (s op) s1 s2)
      )))

(def v+ (s +))
(def v- (s -))
(def v* (s *))
(def vd (s /))

(defn scalar [a b] (apply + (map * a b)))
(defn nthXNom [a b x y] (- (* (nth a x) (nth b y)) (* (nth b x) (nth a y))))
(defn vect [a b] [(nthXNom a b 1 2)
                  (nthXNom a b 2 0)
                  (nthXNom a b 0 1)])

(def m+ (s +))
(def m- (s -))
(def m* (s *))
(def md (s /))

(defn s*v [s v] (mapv (partial * s) v))

(defn x*x [oper] (fn [a b] (mapv (partial oper b) a)))

(def v*s (x*x *))
(def m*s (x*x s*v))
(def m*v (x*x scalar))

(defn transpose [m] (apply mapv vector m))
(defn m*m [m1 m2] (transpose (mapv (partial m*v m1) (transpose m2))))

(def s+ (s +))
(def s- (s -))
(def s* (s *))
(def sd (s /))
