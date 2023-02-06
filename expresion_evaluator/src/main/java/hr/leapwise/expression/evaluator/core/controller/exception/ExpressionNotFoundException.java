package hr.leapwise.expression.evaluator.core.controller.exception;

public class ExpressionNotFoundException extends RuntimeException{

    public ExpressionNotFoundException() {
        super(ExceptionMessage.NOT_FOUND);
    }
}
