package expression;

public class Add extends AbstractExpression {
    String s = "+";

    public Add (Expression exp1, Expression exp2) {
        super(exp1, exp2);
        super.s = s;
    }

    @Override
    public int getValue(int a, int b) {
        return a + b;
    }

}
