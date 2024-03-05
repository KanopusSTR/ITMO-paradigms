// :NOTE: а зачем дефолтное значение "c", если можно обойтись без этого?
function Operation (...args) {
    this.args = args;
    if (args[1] != 0 && args.length !== args[1] + 4) {
        throw new OperatorError("a lot of or not anougth arguments : " + args);
    }
}
Operation.prototype.evaluate = function (x, y, z) {
    let numbers = [];
    for (let i = 4; i < this.args.length; i++) {
        numbers.push(this.args[i].evaluate(x, y, z));
    }
    return this.args[2](numbers);
}
Operation.prototype.diff = function (x) {
    return this.args[3](x, this.args.slice(4, this.args.length));
}
// :NOTE: нужно довести до конца попытку объединения unary и binary в мульти-аргументную функцию от ...args
//        + писать два тернарных оператора подряд - плохой план
Operation.prototype.toString = function () {
    if (this.args[0] === "c") {
        return String(this.args[2]);
    } else if (this.args[0].length > 2) {
        return this.args[4] + " " + this.args[0];
    } else {
        return this.args[4] + " " + this.args[5] + " " + this.args[0];
    }
}
Operation.prototype.prefix = function () {
    if (this.args[0] === "c") {
        return String(this.args[2]);
    }
    let tmp = "(" + this.args[0];
    for (let i = 4; i < this.args.length; i++) {
        // println(this.args.length + " " + this.args + " : args length");
        tmp += " " + this.args[i].prefix();
    }
    tmp += ")";
    return tmp;
}
function Const(...args) {
    Operation.call(this, "c", 0, ...args);
}
Const.prototype = Object.create(Operation.prototype)
// :NOTE: не используются x, y, z
Const.prototype.evaluate = function () {
    return this.args[2];
}
Const.prototype.diff = function () { return new Const(0); }
function Variable(...args) {
    Operation.call(this, "c", 0, ...args);
}
Variable.prototype = Object.create(Operation.prototype)
Variable.prototype.evaluate = function(x, y, z) {
    return this.args[2] === "x" ? x: this.args[2] === "y" ? y: z;
}
Variable.prototype.diff = function (x) { return this.args[2] === x ? new Const(1) : new Const(0); }
function Negate(...args) {
    operation = args => -args[0];
    differ = (x, args) => new Negate(args[0].diff(x));
    Operation.call(this, "negate", 1, operation, differ, ...args);
}
Negate.prototype = Object.create(Operation.prototype)
function Add(...args) {
    operation = arg => arg[0] + arg[1];
    differ = (x, args) => new Add(args[0].diff(x), args[1].diff(x));
    Operation.call(this, "+",  2, operation, differ, ...args)
}
Add.prototype = Object.create(Operation.prototype)
function Subtract(...args) {
    operation = args => args[0] - args[1];
    differ = (x, args) => new Subtract(args[0].diff(x), args[1].diff(x));
    Operation.call(this, "-",  2, operation, differ, ...args);
}
Subtract.prototype = Object.create(Operation.prototype)
function Multiply(...args) {
    operation = args => args[0] * args[1];
    differ = (x, args) => new Add(new Multiply(args[0].diff(x), args[1]), new Multiply(args[1].diff(x), args[0]));
    Operation.call(this, "*",  2, operation, differ, ...args);
}
Multiply.prototype = Object.create(Operation.prototype)
function Divide(...args) {
    operation = args => args[0] / args[1];
    differ = (x, args) => new Divide(new Subtract(new Multiply(args[0].diff(x), args[1]), new Multiply(args[1].diff(x), args[0])), new Multiply(args[1], args[1]));
    Operation.call(this, "/",  2, operation, differ, ...args);
}
Divide.prototype = Object.create(Operation.prototype)
function Sinh(...args) {
    operation = arg => Math.sinh(arg[0]);
    differ = (x, args) => new Multiply(new Cosh(args[0]), args[0].diff(x));
    Operation.call(this, "sinh",  1, operation, differ, ...args);
}
Sinh.prototype = Object.create(Operation.prototype)
function Cosh(...args) {
    operation = args => Math.cosh(args[0]);
    differ = (x, args) => new Multiply(new Sinh(args[0]), args[0].diff(x));
    Operation.call(this, "cosh", 1, operation, differ, ...args);
}
Cosh.prototype = Object.create(Operation.prototype)

function Mean (...args) {
    operation = args => args.reduce(add, 0) / args.length;
    differ = (x, args) => new Divide(addDiff(args, x, a => a), new Const(args.length));
    Operation.call(this, "mean", 0, operation, differ, ...args)
}
Mean.prototype = Object.create(Operation.prototype)

function Var (...args) {
    operation = args => (args.reduce(addSq, 0)/ args.length - Math.pow(args.reduce(add, 0)/ args.length, 2));
    differ = (x, args) => new Subtract(new Divide(addDiff(args, x, a => new Multiply(a, a)), new Const(args.length)),
        new Multiply(new Mean(...args), new Mean(...args)).diff(x));
    Operation.call(this, "var", 0, operation, differ, ...args)
}
Var.prototype = Object.create(Operation.prototype)

function add(accumulator, a) {
    return accumulator + a;
}
function addSq(accumulator, a) {
    return accumulator + Math.pow(a, 2);
}
function addDiff(args, x, operation) {
    let ans;
    if (args.length >= 2) {
        ans = new Add(operation(args[0]).diff(x), operation(args[1]).diff(x));
    } else {
        return operation(args[0]).diff(x);
    }
    for (let i = 2; i < args.length; i++) {
        ans = new Add(ans, operation(args[i]).diff(x));
    }
    return ans;
}


let operations = new Map();
operations.set("+", (args) => new Add(...args))
operations.set("-", (args) => new Subtract(...args))
operations.set("*", (args) => new Multiply(...args))
operations.set("/", (args) => new Divide(...args))
operations.set("negate", (args) => new Negate(...args))
operations.set("sinh", (args) => new Sinh(...args))
operations.set("cosh", (args) => new Cosh(...args))
operations.set("mean", (args) => new Mean(...args))
operations.set("var", (args) => new Var(...args))

function BracketError (massage) {
    Error.call(this, massage);
    this.massage = massage;
}
BracketError.prototype = Object.create(Error.prototype);
function OperatorError (massage) {
    Error.call(this, massage);
    this.massage = massage;
}
OperatorError.prototype = Object.create(Error.prototype);
function MissOperError (massage) {
    Error.call(this, massage);
    this.massage = massage;
}
MissOperError.prototype = Object.create(Error.prototype);


function parse(string) {
    // :NOTE: зачем 2 переменных? можно в одну строку split(" ").filter(...)
    //        или же сразу split(/\s+/)
    let array = String(string.trim()).split(/\s+/);
    let ans = [];
    for (let i = 0; i < array.length; i++) {
        if (!isNaN(array[i])) {
            ans.push(new Const(Number(array[i])));
        } else if (array[i] === "x" || array[i] === "y" || array[i] === "z") {
            ans.push(new Variable(array[i]));
        } else if (array[i].length > 1){
            ans.push(operations.get(array[i])([ans.pop()]));
        } else {
            ans.push(operations.get(array[i])([ans.pop(), ans.pop()].reverse()));
        }
    }
    return ans[0];
}

function parsePrefix(string) {
    let arrayBracketCount = [];
    let arrayVal = [];
    let end = 0;
    let opCl = 0;
    string = string.trim();
    if (string.length === 0) {
        throw new OperatorError("empty input");
    }
    let arrayOp = [];
    if (string[0] !== "(") {
        if (!isNaN(string)) {
            return new Const(Number(string));
        } else if (string === "x" || string === "y" || string === "z") {
            return new Variable(string);
        } else {
            throw new OperatorError("unexpected string");
        }
    }
    for (let i = 0; i < string.length; i++) {
        if (string[i] === " " || string[i] === "(" || string[i] === ")") {
            if ((i - end) !== 0) {
                let str = string.substring(end, i);
                end = i + 1;
                if (!isNaN(str)) {
                    arrayVal.push(new Const(Number(str)));
                    arrayBracketCount[arrayOp.length - 1]++;
                } else if (str === "x" || str === "y" || str === "z") {
                    arrayVal.push(new Variable(str));
                    arrayBracketCount[arrayOp.length - 1]++;
                } else {
                    arrayOp.push(str)
                    arrayBracketCount.push(0)
                }
            } else {
                end++;
            }
        }
        if (string[i] === "(") {
            opCl++;
        } else if (string[i] === ")") {
            opCl--;
            let count = arrayBracketCount.pop();
            if (opCl < 0) {
                throw new BracketError("more close brackets than open");
            }
            if (arrayVal.length < count || arrayVal.length === 0) {
                throw new OperatorError("not anougth arguments");
            }
            let oper = arrayOp.pop();
            arrayBracketCount[arrayOp.length - 1]++;
            let operand = [];
            for (let j = 0; j < count; j++) {
                operand.push(arrayVal.pop());
            }
            try {
                arrayVal.push(operations.get(oper)(operand.reverse()));
            } catch (e) {
                throw new OperatorError("wrong arguments for operations");
            }
        }
    }
    if (opCl !== 0) {
        throw new BracketError("miss brackets : (");
    }
    if (arrayOp.length !== 0 || arrayVal.length > 1 || end !== string.length) {
        throw new MissOperError("miss operations or numbers" + " " + string);
    }
    return arrayVal[0];
}