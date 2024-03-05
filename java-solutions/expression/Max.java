package expression;

public class Max extends AbstractExpression {
    String s = "max";

    public Max (Expression exp1, Expression exp2) {
        super(exp1, exp2);
        super.s = s;
    }

    public int getValue(int a, int b) {
        return Math.max(a, b);
    }
}