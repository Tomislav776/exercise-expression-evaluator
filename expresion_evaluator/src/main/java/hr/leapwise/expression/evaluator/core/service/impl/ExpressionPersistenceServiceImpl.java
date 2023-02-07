package hr.leapwise.expression.evaluator.core.service.impl;

import hr.leapwise.expression.evaluator.core.controller.exception.ExpressionNotFoundException;
import hr.leapwise.expression.evaluator.core.persistence.model.Expression;
import hr.leapwise.expression.evaluator.core.persistence.repository.ExpressionRepository;
import hr.leapwise.expression.evaluator.core.service.ExpressionPersistenceService;
import hr.leapwise.expression.evaluator.core.service.dto.ExpressionDto;
import hr.leapwise.expression.evaluator.core.service.logic.ShuntingYardParser;
import hr.leapwise.expression.evaluator.core.service.mapper.ExpressionPersistenceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ExpressionPersistenceServiceImpl implements ExpressionPersistenceService {

    private final ExpressionPersistenceMapper expressionPersistenceMapper;
    private final ExpressionRepository expressionRepository;
    private final ShuntingYardParser shuntingYardParser;

    public ExpressionPersistenceServiceImpl(ExpressionPersistenceMapper expressionPersistenceMapper,
                                            ExpressionRepository expressionRepository, ShuntingYardParser shuntingYardParser) {
        this.expressionPersistenceMapper = expressionPersistenceMapper;
        this.expressionRepository = expressionRepository;
        this.shuntingYardParser = shuntingYardParser;
    }

    @Override
    public String saveExpression(final ExpressionDto expressionDto) {
        List<String> infixTokens = shuntingYardParser.parseExpression(expressionDto.expression());
        Expression expression = expressionRepository.save(expressionPersistenceMapper.mapToExpression(expressionDto.name(), String.join(" ", infixTokens)));
        return expression.getIdentifier();
    }

    @Override
    public ExpressionDto findByIdentifier(String identifier) {
        Expression expression = expressionRepository.findByIdentifier(identifier).orElseThrow(ExpressionNotFoundException::new);
        return expressionPersistenceMapper.mapToExpressionDto(expression);
    }

}
