package expression;

public class Negate implements Expression, TripleExpression {
    Expression exp;

    public Negate(Expression exp) {
        this.exp = exp;
    }

    @Override
    public int evaluate(int x) {
        return -exp.evaluate(x);
    }

    public int evaluate(int k1, int k2, int k3) {
        return -((TripleExpression) exp).evaluate(k1, k2, k3);
    }

    public String toString() {
        return "-" + "(" + exp.toString() + ")";
    }
}
