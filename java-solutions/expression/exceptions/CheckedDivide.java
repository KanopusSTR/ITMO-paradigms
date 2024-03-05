package expression.exceptions;

import expression.AbstractExpression;
import expression.Expression;

public class CheckedDivide extends AbstractExpression {
    String s = "/";

    public CheckedDivide (Expression exp1, Expression exp2) {
        super(exp1, exp2);
        super.s = s;
    }

    public int getValue(int a, int b) {
        if (b == 0) {
            throw new DivisionByZeroException(a + " / " + b);
        }
        if (a == Integer.MIN_VALUE && b == -1) {
            throw new ArithmeticException("overflow");
        }
        return a / b;
    }
}
