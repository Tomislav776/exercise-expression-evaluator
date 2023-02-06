package hr.leapwise.expression.evaluator.core.controller.exception;

public class ExpressionOperatorNotValidException extends RuntimeException {

    public ExpressionOperatorNotValidException() {
        super(ExceptionMessage.NOT_VALID_OPERATOR);
    }
}
