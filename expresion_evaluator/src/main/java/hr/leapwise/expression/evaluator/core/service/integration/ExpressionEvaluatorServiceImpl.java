package hr.leapwise.expression.evaluator.core.service.integration;

import hr.leapwise.expression.evaluator.core.service.ExpressionEvaluatorService;
import hr.leapwise.expression.evaluator.core.service.ExpressionPersistenceService;
import hr.leapwise.expression.evaluator.core.service.dto.EvaluateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExpressionEvaluatorServiceImpl implements ExpressionEvaluatorService {

    private final ExpressionPersistenceService expressionPersistenceService;

    public ExpressionEvaluatorServiceImpl(ExpressionPersistenceService expressionPersistenceService) {
        this.expressionPersistenceService = expressionPersistenceService;
    }

    @Override
    public Boolean evaluateExpression(EvaluateDto evaluateDto) {
        //ToDO: implement
        return true;
    }
}
