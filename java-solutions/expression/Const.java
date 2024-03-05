package expression;

import java.util.Objects;

public class Const extends AbsMonoExpression {
    int x;

    public Const (int x) {
        super(x);
        this.x = x;
    }

    public String toString() {
        return Integer.toString(x);
    }

    public boolean equals(Object obj) {
        if (obj instanceof AbstractExpression) {
            return false;
        }
        if (obj instanceof Expression) {
            Expression exp = (Expression) obj;
            return exp.toString().equals(Integer.toString(x));
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(x);
    }
}
