package hr.leapwise.expression.evaluator.core.service.impl;

import hr.leapwise.expression.evaluator.core.service.ExpressionEvaluatorService;
import hr.leapwise.expression.evaluator.core.service.ExpressionPersistenceService;
import hr.leapwise.expression.evaluator.core.service.dto.EvaluateDto;
import hr.leapwise.expression.evaluator.core.service.dto.ExpressionDto;
import hr.leapwise.expression.evaluator.core.service.logic.ShuntingYardParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class ExpressionEvaluatorServiceImpl implements ExpressionEvaluatorService {

    private final ExpressionPersistenceService expressionPersistenceService;
    private final ShuntingYardParser shuntingYardParser;

    public ExpressionEvaluatorServiceImpl(ExpressionPersistenceService expressionPersistenceService, ShuntingYardParser shuntingYardParser) {
        this.expressionPersistenceService = expressionPersistenceService;
        this.shuntingYardParser = shuntingYardParser;
    }

    @Override
    public Boolean evaluateExpression(EvaluateDto evaluateDto) {
        ExpressionDto expressionDto = expressionPersistenceService.findByIdentifier(evaluateDto.identifier());
        List<String> infixTokenList = Arrays.stream(expressionDto.expression().split(" ")).toList();
        return shuntingYardParser.evaluateExpression(evaluateDto.json(), infixTokenList);
    }
}
