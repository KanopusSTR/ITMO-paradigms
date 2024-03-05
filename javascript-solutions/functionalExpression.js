let operations = (a, b, operation)  => (...vars) => operation(a(...vars), b(...vars));
let cnst = arg => () => arg;
let variable = arg => (x, y, z) => arg === "x" ? x : (arg === "y" ? y : z);
let negate = (x) => operations(x, cnst(0), x => -x);
let add = (a, b) => operations(a, b, (a, b) => a + b);
let subtract = (a, b) => operations(a, b, (a, b) => a - b);
let multiply = (a, b) => operations(a, b, (a, b) => a * b);
let divide = (a, b) => operations(a, b, (a, b) => a / b);
const pi = cnst(Math.PI);
const e = cnst(Math.E);
let sinh = (x) => operations(x, cnst(0), x => Math.sinh(x));
let cosh = (x) => operations(x, cnst(0), x => Math.cosh(x));

let operationsX = new Map();
operationsX.set("+", (a, b) => add(b, a));
operationsX.set("-", (a, b) => subtract(b, a));
operationsX.set("*", (a, b) => multiply(b, a));
operationsX.set("/", (a, b) => divide(b, a));
operationsX.set("negate", (a) => negate(a));
operationsX.set("sinh", (a) => sinh(a));
operationsX.set("cosh", (a) => cosh(a));

function parse(string) {
    let array = String(string.trim()).split(/\s+/);
    let ans = [];
    for (let i = 0; i < array.length; i++) {
        if (!isNaN(array[i])) {
            ans.push(cnst(Number(array[i])));
        } else if (array[i] === "x" || array[i] === "y" || array[i] === "z") {
            ans.push(variable(array[i]));
        } else if (array[i] === "pi") {
            ans.push(pi);
        } else if (array[i] === "e") {
            ans.push(e);
        } else if (array[i].length > 1){
            ans.push(operationsX.get(array[i])(ans.pop()));
        } else {
            ans.push(operationsX.get(array[i])(ans.pop(), ans.pop()));
        }
    }
    return ans[0];
}