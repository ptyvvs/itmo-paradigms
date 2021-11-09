"use strict"

function BinaryAbstract(a, b, f, sign) {
    this.a = a;
    this.b = b;
    this.f = f;
    this.sign = sign;
}

BinaryAbstract.prototype = {
    evaluate: function (x, y, z) {
        return this.f(this.a.evaluate(x, y, z), this.b.evaluate(x, y, z))
    },
    toString: function () {
        return this.a.toString() + " " + this.b.toString() + " " + this.sign.toString()
    },
    prefix: function () {
        return "(" + this.sign.toString() + ' ' + this.a.prefix() + ' ' + this.b.prefix() + ")";
    }
}

function UnaryAbstract(value, f, sign) {
    this.value = value;
    this.f = f;
    this.sign = sign;
}

UnaryAbstract.prototype = {
    evaluate: function (x, y, z) {
        return this.f(this.value.evaluate(x, y, z));
    },
    toString: function () {
        return this.value.toString() + " " + this.sign.toString();
    },
    prefix: function () {
        return "(" + this.sign.toString() + " " + this.value.prefix() + ")";
    }
}

function createBinaryOperation(f, sign) {
    function res(a, b) {
        return new BinaryAbstract(a, b, f, sign);
    }

    return res;
}

function createUnaryOperation(f, sign) {
    function res(a) {
        return new UnaryAbstract(a, f, sign);
    }

    return res;
}

const ArcTan = createUnaryOperation(Math.atan, "atan");
const Negate = createUnaryOperation(a => -1 * a, "negate");
const Add = createBinaryOperation((a, b) => a + b, "+");
const Subtract = createBinaryOperation((a, b) => a - b, "-");
const Multiply = createBinaryOperation((a, b) => a * b, "*");
const ArcTan2 = createBinaryOperation(Math.atan2, "atan2");
const Divide = createBinaryOperation((a, b) => a / b, "/");
const Sinh = createUnaryOperation(Math.sinh, "sinh")
const Cosh = createUnaryOperation(Math.cosh, "cosh")

function Variable(name) {
    this.name = name;
}

Variable.prototype = {
    evaluate: function (x, y, z) {
        if (this.name === "x") {
            return x;
        } else if (this.name === "y") {
            return y;
        } else if (this.name === "z") {
            return z;
        }
    },
    toString: function () {
        return this.name.toString()
    },
    prefix: function () {
        return this.name.toString();
    }

}

function Const(value) {
    this.value = value;
}

Const.prototype = {
    evaluate: function (x, y, z) {
        return this.value
    },
    toString: function () {
        return this.value.toString()
    },
    prefix: function () {
        return this.value.toString()
    }
}
const binOp = new Map([["+", Add], ["-", Subtract], ["*", Multiply], ["/", Divide], ["atan2", ArcTan2]]);
const unOp = new Map([["negate", Negate], ["atan", ArcTan], ["sinh", Sinh], ["cosh", Cosh]]);
const vars = new Map([["x", "x"], ["y", "y"], ["z", "z"]]);

function parsePrefix(s) {
    s = s + ' ';
    let ind = 0;
    let operations = [];
    let nums = [];
    let balance = 0;
    let token = "";
    while (ind < s.length) {
        if (s.charAt(ind) !== ' ' && s.charAt(ind) !== '(' && s.charAt(ind) !== ')') {
            token += s.charAt(ind);
        } else {
            if (token.length > 0) {
                if (vars.has(token)) {
                    nums.push(new Variable(token));
                } else if (unOp.has(token) || binOp.has(token)) {
                    operations.push(token);
                } else if (isDigit(token)) {
                    nums.push(new Const(token - 0));
                } else {
                    throw new MyError("Неизвестный символ в позиции " + ind.toString());
                }
                token = "";
            }
            if (s.charAt(ind) === "(") {
                balance++;
            } else if (s.charAt(ind) === ")") {
                if (nums.length === 0) {
                    throw new MyError("Недостаточное количество чисел или переменных");
                }
                let op = operations.pop();
                if (binOp.has(op)) {
                    if (nums.length < 2) {
                        throw new MyError("Недостаточное количество чисел или переменных в операции \"" + op + "\"");
                    }
                }
                let r = nums.pop();
                if (unOp.has(op)) {
                    let f = unOp.get(op);
                    nums.push(new f(r));
                } else if (binOp.has(op)) {
                    let f = binOp.get(op);
                    let l = nums.pop();
                    nums.push(new f(l, r));
                } else {
                    throw new MyError("Неизвестная операция или недостаточное количество операций")
                }
                balance--;
            }
        }
        ind++;
    }
    if (balance !== 0) {
        throw new MyError("Неправильное количество скобок");
    }

    if (nums.length !== 1 || operations.length > 0) {
        throw new MyError("Неправильное количество операций и/или операндов");
    }
    return nums.shift();
}

function isDigit(s) {
    let i = 0;
    if (s.charAt(0) === '-') {
        i++;
    }
    for (i; i < s.length; i++) {
        if (s.charAt(i) < '0' || s.charAt(i) > '9') {
            return false;
        }
    }
    return true;
}

function MyError(message) {
    this.message = message;
}

MyError.prototype = Object.create(Error.prototype);
MyError.prototype.name = "MyError"
MyError.prototype.constructor = MyError;
