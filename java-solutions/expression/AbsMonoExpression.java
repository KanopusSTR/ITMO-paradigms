package expression;

public abstract class AbsMonoExpression implements Expression, TripleExpression {
    int x = 0;
    String s = "a";
    public AbsMonoExpression (int x) {
        this.x = x;
    }
    public AbsMonoExpression (String s) {
        this.s = s;
    }

    public int evaluate(int k) {
        if (s.equals("x")) {
            return k;
        } else {
            return x;
        }
    }

    public char getS() {
        throw new UnsupportedOperationException("getS");
    }

    public int evaluate(int k1, int k2, int k3) {
        if (s.equals("x")) {
            return  k1;
        } else if (s.equals("y")) {
            return k2;
        } else if (s.equals("z")) {
            return k3;
        } else {
            return x;
        }
    }
}
