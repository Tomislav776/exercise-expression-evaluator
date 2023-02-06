package hr.leapwise.expression.evaluator.core.controller.exception;

public class ExpressionStructureNotValidException extends RuntimeException {

    public ExpressionStructureNotValidException() {
        super(ExceptionMessage.NOT_VALID_STRUCTURE);
    }
}
