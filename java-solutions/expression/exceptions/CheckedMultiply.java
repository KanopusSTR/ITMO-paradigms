package expression.exceptions;

import expression.AbstractExpression;
import expression.Expression;

public class CheckedMultiply extends AbstractExpression {
    String s = "*";

    public CheckedMultiply (Expression exp1, Expression exp2) {
        super(exp1, exp2);
        super.s = s;
    }

    public int getValue(int a, int b) {
        int x = a * b;
        if (a != 0 && b != 0) {
            if ((a == -1 && b == Integer.MIN_VALUE)
             || (b == -1 && a == Integer.MIN_VALUE)
             || (x / a != b)) {
                throw new ArithmeticException("overflow");
            }

        } else {
            return 0;
        }
        return x;
    }
}
