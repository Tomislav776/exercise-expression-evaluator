package hr.leapwise.expression.evaluator.core.controller.exception;

public class ValueIsNotNumberException extends RuntimeException {

    public ValueIsNotNumberException() {
        super(ExceptionMessage.NOT_VALID_STRUCTURE);
    }
}
