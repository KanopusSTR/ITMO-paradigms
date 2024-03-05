package expression.exceptions;

import expression.*;

public final class ExpressionParser implements TripleParser {
    public TripleExpression parse(final String source) {
        return parse(new ExpressionSource(source));
    }

    public TripleExpression parse(final CharSource source) {
        return (TripleExpression) new ExpressionParse(source).parseExpression();
    }

    private static class ExpressionParse extends BaseParser {

        Expression result = null;

        public ExpressionParse(CharSource source) {
            super(source);
        }

        public Expression parseExpression() {
            parseElement(1);
            while (!eof()) {
                parseElement(0);
            }
            return result;
        }

        private void parseElement(int i) {
            skipWhitespace();
            result = parseValue(result, i);
            skipWhitespace();
        }

        private Expression parseValue(Expression result, int un) {
            if (un == 0 && take('-')) {
                result = new CheckedSubtract(result, parseBlock('-'));

            } else if (un == 0 && take('+')) {
                result = new CheckedAdd(result, parseBlock('+'));

            } else if (un == 0 && take('*')) {
                result = new CheckedMultiply(result, parseBlock('*'));

            } else if (un == 0 && take('/')) {
                result = new CheckedDivide(result, parseBlock('/'));

            } else if (un == 0 && take('m')) {
                if (take('i')) {
                    if (take('n')) {
                        result = new Min(result, parseBlock('m'));
                    }
                } else if (take('a')) {
                    if (take('x')) {
                        result = new Max(result, parseBlock('M'));
                    }
                }

            } else {
                if (un == 0) {
                    throw new ParserException("miss operation");
                }
                result = parseBlock('c');
            }
            return result;
        }

        private Expression parseBlock(char priority) {
            skipWhitespace();
            Expression exp;
            StringBuilder sb = new StringBuilder();
            if (take('-')) {
                if (between('1', '9')) {
                    takeDigits(sb);
                    skipWhitespace();
                    int number;
                    try {
                        number = -Integer.parseUnsignedInt(sb.toString());
                        if (number != Integer.MIN_VALUE) {
                            number = -Integer.parseInt(sb.toString());
                        }
                    } catch (IllegalArgumentException e) {
                        throw new ArithmeticException("overflow");
                    }
                    exp = new Const(number);
                    while (!eof() &&  priority != 'c' && priority(current()) > priority(priority)) {
                        exp = parseValue(exp, 0);
                    }
                } else {
                    exp = new CheckedNegate(parseValue(null, 1));
                    skipWhitespace();
                    while (!eof() && priority(current()) > priority(priority) && priority(priority) != 0) {
                        skipWhitespace();
                        exp = parseValue(exp, 0);
                        skipWhitespace();
                    }
                }
                return exp;
            }
            if (take('(')) {
                skipWhitespace();
                exp = parseValue(null, 1);
                skipWhitespace();
                while (!take(')')) {
                    exp = parseValue(exp, 0);
                    skipWhitespace();
                }
                skipWhitespace();
                while (!eof() && priority != 'c' && priority(current()) > priority(priority)) {
                    skipWhitespace();
                    exp = parseValue(exp, 0);
                    skipWhitespace();
                }
                return exp;
            } else if (take('x')) {
                skipWhitespace();
                exp = new Variable("x");
                while (!eof() && priority != 'c' && priority(current()) > priority(priority)) {
                    skipWhitespace();
                    exp = parseValue(exp, 0);
                    skipWhitespace();
                }
                return exp;
            } else if (take('y')) {
                skipWhitespace();
                exp = new Variable("y");
                while (!eof() && priority != 'c' && priority(current()) > priority(priority)) {
                    skipWhitespace();
                    exp = parseValue(exp, 0);
                    skipWhitespace();
                }
                return exp;
            } else if (take('z')) {
                skipWhitespace();
                exp = new Variable("z");
                while (!eof() && priority != 'c' && priority(current()) > priority(priority)) {
                    exp = parseValue(exp, 0);
                    skipWhitespace();
                }
                return exp;
            } else {
                if (between('0', '9')) {
                    takeDigits(sb);
                    skipWhitespace();
                    int number;
                    try {
                        number = Integer.parseInt(sb.toString());
                    } catch (IllegalArgumentException e) {
                        throw new ArithmeticException("overflow");
                    }
                    exp = new Const(number);
                    while (!eof() && priority != 'c' && priority(current()) > priority(priority)) {
                        exp = parseValue(exp, 0);
                    }
                    return exp;
                } else {
                    throw error("Invalid number");
                }
            }
        }

        private void takeDigits(final StringBuilder sb) {
            while (between('0', '9')) {
                sb.append(take());
            }
        }

        private void skipWhitespace() {
            while (whiteDel() || take('\r') || take('\n')) {
                // skip
            }
        }

        private int priority (char ch) {
            if (ch == '-' || ch == '+') {
                return 2;
            } else if (ch == '*' || ch == '/') {
                return 3;
            } else if (ch == 'm' || ch == 'M') {
                return 1;
            } else {
                return 0;
            }
        }
    }
}