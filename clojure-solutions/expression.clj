(defn binaryOperations [f] (fn [a b] (fn [m] (f (a m) (b m)))))
(defn unaryOperations [f] (fn [a] (fn [m] (f (a m)))))
(defn constant [a] (fn [m] a))
(defn variable [name] (fn [m] (m name)))
(def add (binaryOperations +))
(def subtract (binaryOperations -))
(def multiply (binaryOperations *))
(def divide (binaryOperations (fn [a b] (/ a (double b)))))
(def sin (unaryOperations (fn [q] (Math/sin q))))
(def cos (unaryOperations (fn [q] (Math/cos q))))
(def negate (unaryOperations (fn [q] (- q))))

(def Operations {'+ add '- subtract '* multiply '/ divide 'sin sin 'cos cos 'negate negate 'var variable 'const constant})

(defn parseList [s m] (cond (symbol? s) ((m 'var) (str s))
                            (number? s) ((m 'const) s)
                            :else (if (contains? m (first s))
                                    (apply (m (first s)) (mapv (fn [s] (parseList s m)) (rest s))))))
(defn parseFunction [s] (parseList (read-string s) Operations))


(declare Sin Cos zero one)
(defn objOp [a b m f] (f (.evaluateImpl a m) (.evaluateImpl b m)))
(defn objToStrBin [a b sign] (str "(" sign " " (.toStringImpl a) " " (.toStringImpl b) ")"))
(defn objToStrUn [a sign] (str "(" sign " " (.toStringImpl a) ")"))
(definterface Operation
  (evaluateImpl [m])
  (toStringImpl [])
  (diffImpl [v]))
(deftype AddImpl [a b]
  Operation
  (evaluateImpl [this m] (objOp a b m +))
  (toStringImpl [this] (objToStrBin a b "+"))
  (diffImpl [this v] (AddImpl. (.diffImpl a v) (.diffImpl b v))))
(deftype ConstantImpl [value]
  Operation
  (evaluateImpl [this m] value)
  (toStringImpl [this] (str value))
  (diffImpl [this v] zero))
(deftype VariableImpl [name]
  Operation
  (evaluateImpl [this m] (m name))
  (toStringImpl [this] (str name))
  (diffImpl [this v] (if (= v name) one zero)))
(deftype SubtractImpl [a b]
  Operation
  (evaluateImpl [this m] (objOp a b m -))
  (toStringImpl [this] (objToStrBin a b "-"))
  (diffImpl [this v] (SubtractImpl. (.diffImpl a v) (.diffImpl b v))))
(deftype MultiplyImpl [a b]
  Operation
  (evaluateImpl [this m] (objOp a b m *))
  (toStringImpl [this] (objToStrBin a b "*"))
  (diffImpl [this v] (AddImpl. (MultiplyImpl. (.diffImpl a v) b) (MultiplyImpl. a (.diffImpl b v)))))
(deftype DivideImpl [a b]
  Operation
  (evaluateImpl [this m] (objOp a b m (fn [q r] (/ q (double r)))))
  (toStringImpl [_] (objToStrBin a b "/"))
  (diffImpl [this v] (DivideImpl. (SubtractImpl. (MultiplyImpl. (.diffImpl a v) b) (MultiplyImpl. a (.diffImpl b v))) (MultiplyImpl. b b))))
(deftype NegateImpl [a]
  Operation
  (evaluateImpl [this m] (* -1 (.evaluateImpl a m)))
  (toStringImpl [this] (objToStrUn a "negate"))
  (diffImpl [this v] (NegateImpl. (.diffImpl a v))))
(defn Add [a b] (AddImpl. a b))
(defn Subtract [a b] (SubtractImpl. a b))
(defn Multiply [a b] (MultiplyImpl. a b))
(defn Divide [a b] (DivideImpl. a b))
(defn Negate [a] (NegateImpl. a))
(defn Constant [a] (ConstantImpl. a))
(defn Variable [name] (VariableImpl. name))
(defn evaluate [a m] (.evaluateImpl a m))
(defn diff [a v] (.diffImpl a v))
(defn toString [a] (.toStringImpl a))
(def zero (ConstantImpl. 0))
(def one (ConstantImpl. 1))
(deftype SinImpl [a]
  Operation
  (evaluateImpl [this m] (Math/sin (.evaluateImpl a m)))
  (toStringImpl [this] (objToStrUn a "sin"))
  (diffImpl [this v] (MultiplyImpl. (Cos a) (.diffImpl a v))))
(deftype CosImpl [a]
  Operation
  (evaluateImpl [this m] (Math/cos (.evaluateImpl a m)))
  (toStringImpl [this] (objToStrUn a "cos"))
  (diffImpl [this v] (MultiplyImpl. (NegateImpl. (Sin a)) (.diffImpl a v))))
(defn Cos [a] (CosImpl. a))
(defn Sin [a] (SinImpl. a))

(def OperationsObj{'+ Add '- Subtract '* Multiply '/ Divide  'negate Negate 'sin Sin 'cos Cos 'const Constant 'var Variable})

(defn parseObject [s] (parseList (read-string s) OperationsObj))



