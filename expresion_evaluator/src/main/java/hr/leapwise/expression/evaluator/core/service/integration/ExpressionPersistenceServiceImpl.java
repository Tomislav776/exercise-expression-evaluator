package hr.leapwise.expression.evaluator.core.service.integration;

import hr.leapwise.expression.evaluator.core.controller.exception.ExpressionNotFoundException;
import hr.leapwise.expression.evaluator.core.persistence.model.Expression;
import hr.leapwise.expression.evaluator.core.persistence.repository.ExpressionRepository;
import hr.leapwise.expression.evaluator.core.service.ExpressionPersistenceService;
import hr.leapwise.expression.evaluator.core.service.dto.ExpressionDto;
import hr.leapwise.expression.evaluator.core.service.mapper.ExpressionPersistenceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExpressionPersistenceServiceImpl implements ExpressionPersistenceService {

    private final ExpressionPersistenceMapper expressionPersistenceMapper;
    private final ExpressionRepository expressionRepository;

    public ExpressionPersistenceServiceImpl(ExpressionPersistenceMapper expressionPersistenceMapper,
                                            ExpressionRepository expressionRepository) {
        this.expressionPersistenceMapper = expressionPersistenceMapper;
        this.expressionRepository = expressionRepository;
    }

    @Override
    public String saveExpression(final ExpressionDto expressionDto) {
        Expression expression = expressionRepository.save(expressionPersistenceMapper.mapToExpression(expressionDto));
        return expression.getIdentifier();
    }

    @Override
    public ExpressionDto findByIdentifier(String identifier) {
        Expression expression = expressionRepository.findByIdentifier(identifier).orElseThrow(ExpressionNotFoundException::new);
        return expressionPersistenceMapper.mapToExpressionDto(expression);
    }
}
