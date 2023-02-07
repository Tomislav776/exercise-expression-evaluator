package hr.leapwise.expression.evaluator.core.controller.exception;

public class ValueCanNotBeNullException extends RuntimeException {

    public ValueCanNotBeNullException() {
        super(ExceptionMessage.IS_NULL);
    }
}
