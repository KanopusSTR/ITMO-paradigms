package expression.exceptions;

import expression.AbstractExpression;
import expression.Expression;

public class CheckedSubtract extends AbstractExpression {
    String s = "-";

    public CheckedSubtract (Expression exp1, Expression exp2) {
        super(exp1, exp2);
        super.s = s;
    }

    public int getValue(int a, int b) {
        if ((b < 0 && a > Integer.MAX_VALUE + b) || (b > 0 && a < Integer.MIN_VALUE + b)) {
            throw new ArithmeticException("overflow");
        }
        return a - b;
    }
}
