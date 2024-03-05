package expression;

public class Min extends AbstractExpression {
    String s = "min";

    public Min (Expression exp1, Expression exp2) {
        super(exp1, exp2);
        super.s = s;
    }

    public int getValue(int a, int b) {
        return Math.min(a, b);
    }
}