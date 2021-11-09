"use strict";
const binaryOperation = q => (f, g) => (x, y, z) => q(f(x, y, z), g(x, y, z));
const unary = f => value => (x, y, z) => f(value, x, y, z);
const cnst = unary((value, x, y, z) => value);
const variable = unary((value, x, y, z) => {
    if (value === "x") {
        return x;
    } else if (value === "y"){
        return y;
    } else {
        return z;
    }
});
const negate = unary((value, x, y, z) => - value(x, y, z));
const add = binaryOperation((a, b) => a + b);
const subtract = binaryOperation((a, b) => a - b);
const multiply = binaryOperation((a, b) => a * b);
const divide = binaryOperation((a, b) => a / b);