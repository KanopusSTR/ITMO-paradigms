package expression.exceptions;

import expression.Expression;
import expression.TripleExpression;

public class CheckedNegate implements Expression, TripleExpression {
    Expression exp;

    public CheckedNegate(Expression exp) {
        this.exp = exp;
    }

    @Override
    public int evaluate(int x) {
        int ans = exp.evaluate(x);
        if (ans == Integer.MIN_VALUE) {
            throw new ArithmeticException("overflow");
        }
        return -ans;
    }

    @Override
    public int evaluate(int k1, int k2, int k3) {
        int ans = ((TripleExpression) exp).evaluate(k1, k2, k3);
        if (ans == Integer.MIN_VALUE) {
            throw new ArithmeticException("overflow");
        }
        return -ans;
    }

    public String toString() {
        return "-" + "(" + exp.toString() + ")";
    }
}
