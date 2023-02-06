package hr.leapwise.expression.evaluator.core.controller.mapper;

import hr.leapwise.expression.evaluator.core.controller.request.EvaluateRequest;
import hr.leapwise.expression.evaluator.core.controller.request.ExpressionRequest;
import hr.leapwise.expression.evaluator.core.service.dto.EvaluateDto;
import hr.leapwise.expression.evaluator.core.service.dto.ExpressionDto;
import org.springframework.stereotype.Component;

@Component
public class ExpressionControllerMapper {

    public ExpressionDto mapToExpressionDto(ExpressionRequest expressionRequest) {
        return new ExpressionDto(expressionRequest.name(), expressionRequest.expression());
    }

    public EvaluateDto mapToEvaluateDto(EvaluateRequest evaluateRequest, String identifier) {
        return new EvaluateDto(identifier, evaluateRequest.json());
    }

}
