package expression.exceptions;

import expression.AbstractExpression;
import expression.Expression;

public class CheckedAdd extends AbstractExpression {
    String s = "+";

    public CheckedAdd (Expression exp1, Expression exp2) {
        super(exp1, exp2);
        super.s = s;
    }

    @Override
    public int getValue(int a, int b) {
        if ((a > 0 && (b > (Integer.MAX_VALUE - a))) || (a < 0 && b < Integer.MIN_VALUE - a)) {
            throw new ArithmeticException("overflow");
        }
        return a + b;
    }
}
