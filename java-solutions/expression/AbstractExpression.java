package expression;

import java.util.Objects;

public abstract class AbstractExpression implements Expression, TripleExpression {
    Expression exp1;
    Expression exp2;
    TripleExpression texp1;
    TripleExpression texp2;
    protected String s;

    public AbstractExpression (Expression exp1, Expression exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        texp1 = (TripleExpression) exp1;
        texp2 = (TripleExpression) exp2;
    }

    public int evaluate(int k) {
        return getValue(exp1.evaluate(k), exp2.evaluate(k));
    }

    public int evaluate(int k1, int k2, int k3) {
//        System.err.println(texp1 + " " + s + " " + texp2);
         return getValue(texp1.evaluate(k1, k2, k3), texp2.evaluate(k1, k2, k3));
    }

    public String toString() {
        return "(" + exp1.toString() + " " + s + " " + exp2.toString() + ")";
    }

    private Expression getExp1 () {
        return exp1;
    }

    private Expression getExp2 () {
        return exp2;
    }

    private String getS() {
        return s;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AbstractExpression) {
            AbstractExpression exp = (AbstractExpression) obj;
            return exp1.equals(exp.getExp1())
                    && exp2.equals(exp.getExp2())
                    && Objects.equals(s, exp.getS());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(exp1, exp2, s);
    }

    public int getValue(int a, int b) {
        return 0;
    }
}
