package hr.leapwise.expression.evaluator.core.service.mapper;

import hr.leapwise.expression.evaluator.core.persistence.model.Expression;
import hr.leapwise.expression.evaluator.core.service.dto.ExpressionDto;
import org.springframework.stereotype.Component;

@Component
public class ExpressionPersistenceMapper {

    public Expression mapToExpression(final String name, final String infixTokens) {
        return new Expression(name, infixTokens);
    }

    public ExpressionDto mapToExpressionDto(final Expression expression) {
        return new ExpressionDto(expression.getId(), expression.getIdentifier(), expression.getName(), expression.getExpression());
    }
}
