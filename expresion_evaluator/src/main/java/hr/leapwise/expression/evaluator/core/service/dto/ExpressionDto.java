package hr.leapwise.expression.evaluator.core.service.dto;

public record ExpressionDto(Long id, String identifier, String name, String expression) {
    public ExpressionDto(String name, String expression) {
        this(null, null, name, expression);
    }
}
