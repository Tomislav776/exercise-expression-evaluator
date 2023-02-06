package hr.leapwise.expression.evaluator.core.service;

import hr.leapwise.expression.evaluator.core.service.dto.EvaluateDto;

public interface ExpressionEvaluatorService {

    Boolean evaluateExpression(EvaluateDto evaluateDto);

}
