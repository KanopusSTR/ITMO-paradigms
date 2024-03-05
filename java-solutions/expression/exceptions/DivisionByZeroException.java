package expression.exceptions;

public class DivisionByZeroException extends ArithmeticException {
    public DivisionByZeroException (String errorMassage) {
        super(errorMassage);
    }
}
