(defn constant [l] (fn [_] l))
(defn variable [l] (fn [a] (a l (a (keyword l)))))

(defn operation [sign]
  (cond
    (= sign 'negate) (fn [a] (fn [x] (- 0 (a x))))
    :else (fn [a b] (fn [x]
                      (cond
                        (= sign 'log) (/ (Math/log (Math/abs (double (b x)))) (Math/log (Math/abs (double (a x)))))
                        (= sign 'pow) (Math/pow (double (a x)) (double (b x)))
                        (= sign /) (/ (double (a x)) (double (b x)))
                        :else (sign (double (a x)) (double (b x))))))))

(def negate (operation 'negate))
(def add (operation +))
(def subtract (operation -))
(def multiply (operation *))
(def divide (operation /))
(def pow (operation 'pow))
(def log (operation 'log))

(def operFunc
  {
   "negate" negate
   "+"      add
   "-"      subtract
   "*"      multiply
   "/"      divide
   "pow"    pow
   "log"    log
   })

(defn parseFunction [x]
  (let [y (read-string (str x))]
    (cond
      (number? y) (constant y)
      (symbol? y) (variable (str y))
      :else
      (cond
        (= (count y) 3) ((operFunc (str (nth y 0))) (parseFunction (nth y 1)) (parseFunction (nth y 2)))
        :else (negate (parseFunction (nth y 1)))))
    ))

;(load-file "parser.clj")
(load-file "proto.clj")

(def x (field :x))
(def y (field :y))
(def sign (field :sign))
(def args (field :args))
(def evaluate (method :evaluate))
(def diff (method :diff))
(def toString (method :toString))
(def parseObject (method :parseObject))
(declare Operation)
(declare Constant)
(declare Variable)
(declare Negate)
(declare Add)
(declare Subtract)
(declare Multiply)
(declare Divide)
(declare Pow)
(declare Log)
(declare operObj)

(def OperationPrototype
  {
   :evaluate (fn [this vars] ((resolve (symbol (sign this))) (double (evaluate (x this) vars)) (double (evaluate (y this) vars))))
   :diff     (fn [this var] ((operObj (sign this)) (diff (x this) var) (diff (y this) var)))
   :toString (fn [this] (str "(" (sign this) " " (toString (x this)) " " (toString (y this)) ")"))
   })

(defn _Operation [this & args]
  (assoc this
    :args args))
(def Operation (constructor _Operation OperationPrototype))

(def VariablePrototype
  (assoc OperationPrototype
    :evaluate (fn [this vars] ((variable (x this)) vars))
    :diff (fn [this vars] (cond
                            (= vars (x this)) (Constant 1)
                            :else (Constant 0)
                            ))
    :toString (fn [this] (str (x this)))
    ))
(defn _Variable [this x]
  (assoc (_Operation this x)
    :x x
    ))
(def Variable (constructor _Variable VariablePrototype))

(def ConstantPrototype
  (assoc OperationPrototype
    :evaluate (fn [this & _] (x this))
    :diff (fn [_ _] (Constant 0))
    :toString (fn [this] (str (x this)))
    ))
(defn _Constant [this x]
  (assoc (_Operation this x)
    :x x
    ))
(def Constant (constructor _Constant ConstantPrototype))

(defn _Add [this x y]
  (assoc (_Operation this x y)
    :sign "+"
    :x x
    :y y
    ))
(def Add (constructor _Add OperationPrototype))

(defn _Subtract [this x y]
  (assoc (_Operation this x y)
    :sign "-"
    :x x
    :y y
    ))
(def Subtract (constructor _Subtract OperationPrototype))

(def MultiplyPrototype
  (assoc OperationPrototype
    :diff (fn [this var] (Add (Multiply (diff (x this) var) (y this))
                              (Multiply (diff (y this) var) (x this))
                              ))
    ))
(defn _Multiply [this x y]
  (assoc (_Operation this x y)
    :sign "*"
    :x x
    :y y
    ))
(def Multiply (constructor _Multiply MultiplyPrototype))

(def DividePrototype
  (assoc OperationPrototype
    :evaluate (fn [this vars] (/ (double (evaluate (x this) vars)) (double (evaluate (y this) vars))))
    :diff (fn [this var] (Divide
                           (Subtract
                             (Multiply (diff (x this) var) (y this))
                             (Multiply (diff (y this) var) (x this))
                             )
                           (Multiply (y this) (y this))
                           )
            )
    ))
(defn _Divide [this x y]
  (assoc (_Operation this x y)
    :sign "/"
    :x x
    :y y
    ))
(def Divide (constructor _Divide DividePrototype))

(def NegatePrototype
  (assoc OperationPrototype
    :evaluate (fn [this vars]
                (- 0 (evaluate (x this) vars)))
    :diff (fn [this var] (Negate (diff (x this) var)))
    :toString (fn [this] (str "(negate " (toString (x this)) ")"))
    ))
(defn _Negate [this x]
  (assoc (_Operation this x)
    :sign "negate"
    :x x
    ))
(def Negate (constructor _Negate NegatePrototype))

(def PowPrototype
  (assoc OperationPrototype
    :evaluate (fn [this vars]
                (Math/pow (evaluate (x this) vars) (evaluate (y this) vars)))
    :diff (fn [this var] (Multiply (Pow (x this) (y this))
                                   (diff (Multiply (Log (Constant Math/E) (x this))
                                                   (y this)
                                                   ) var)
                                   ))
    ))
(defn _Pow [this x y]
  (assoc (_Operation this x y)
    :sign "pow"
    :x x
    :y y
    ))
(def Pow (constructor _Pow PowPrototype))

(def LogPrototype
  (assoc OperationPrototype
    :evaluate (fn [this vars]
                (/ (Math/log (Math/abs (double (evaluate (y this) vars)))) (Math/log (Math/abs (double (evaluate (x this) vars))))))
    :diff (fn [this var] (Divide
                           (Subtract
                             (Divide (diff (y this) var) (y this))
                             (Multiply (Divide (diff (x this) var) (x this))
                                       (Log (x this) (y this))))
                           (Log (Constant Math/E) (x this))
                           ))
    ))
(defn _Log [this x y]
  (assoc (_Operation this x y)
    :sign "log"
    :x x
    :y y
    ))
(def Log (constructor _Log LogPrototype))

(def operObj
  {
   "negate" Negate
   "+"      Add
   "-"      Subtract
   "*"      Multiply
   "/"      Divide
   "pow"    Pow
   "log"    Log
   })

(defn parseObject [x]
  (let [y (read-string (str x))]
    (cond
      (number? y) (Constant y)
      (symbol? y) (Variable (str y))
      :else
      (cond
        (= (count y) 3) ((operObj (str (nth y 0))) (parseObject (nth y 1)) (parseObject (nth y 2)))
        :else (Negate (parseObject (nth y 1)))))
    ))