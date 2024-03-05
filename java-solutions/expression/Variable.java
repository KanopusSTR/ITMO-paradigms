package expression;

import java.util.Objects;

public class Variable extends AbsMonoExpression {
    String s;

    public Variable (String s) {
        super(s);
        this.s = s;
    }

    public String toString() {
        return s;
    }

    public boolean equals(Object obj) {
        if (obj instanceof AbstractExpression) {
            return false;
        }
        if (obj instanceof Expression) {
            Expression exp = (Expression) obj;
            return exp.toString().equals(s);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(s);
    }
}
