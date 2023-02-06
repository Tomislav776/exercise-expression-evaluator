package hr.leapwise.expression.evaluator.core.service;

import hr.leapwise.expression.evaluator.core.service.dto.ExpressionDto;

public interface ExpressionPersistenceService {

    String saveExpression(final ExpressionDto expressionDto);

    ExpressionDto findByIdentifier(final String identifier);

}
