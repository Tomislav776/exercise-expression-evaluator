package hr.leapwise.expression.evaluator.core.service.mapper;

import hr.leapwise.expression.evaluator.core.persistence.model.Expression;
import hr.leapwise.expression.evaluator.core.service.dto.ExpressionDto;
import org.springframework.stereotype.Component;

@Component
public class ExpressionPersistenceMapper {

    public Expression mapToExpression(final ExpressionDto expressionDto) {
        return new Expression(expressionDto.name(), expressionDto.expression());
    }

    public ExpressionDto mapToExpressionDto(final Expression expression) {
        return new ExpressionDto(expression.getId(), expression.getIdentifier(), expression.getName(), expression.getExpression());
    }
}
