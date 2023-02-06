package hr.leapwise.expression.evaluator.core.controller;

import hr.leapwise.expression.evaluator.core.controller.mapper.ExpressionControllerMapper;
import hr.leapwise.expression.evaluator.core.controller.request.EvaluateRequest;
import hr.leapwise.expression.evaluator.core.controller.request.ExpressionRequest;
import hr.leapwise.expression.evaluator.core.controller.response.EvaluateResponse;
import hr.leapwise.expression.evaluator.core.controller.response.ExpressionResponse;
import hr.leapwise.expression.evaluator.core.service.ExpressionEvaluatorService;
import hr.leapwise.expression.evaluator.core.service.ExpressionPersistenceService;
import hr.leapwise.expression.evaluator.core.service.dto.EvaluateDto;
import hr.leapwise.expression.evaluator.core.service.dto.ExpressionDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ExpressionController {

    private final ExpressionEvaluatorService expressionEvaluatorService;
    private final ExpressionPersistenceService expressionPersistenceService;
    private final ExpressionControllerMapper expressionControllerMapper;

    public ExpressionController(ExpressionEvaluatorService expressionEvaluatorService,
                                ExpressionPersistenceService expressionPersistenceService,
                                ExpressionControllerMapper expressionControllerMapper) {
        this.expressionEvaluatorService = expressionEvaluatorService;
        this.expressionPersistenceService = expressionPersistenceService;
        this.expressionControllerMapper = expressionControllerMapper;
    }

    @PostMapping("/expression")
    public ResponseEntity<ExpressionResponse> createExpression(@RequestBody @Valid ExpressionRequest expressionRequest) {
        ExpressionDto expressionDto = expressionControllerMapper.mapToExpressionDto(expressionRequest);
        String expressionKey = expressionPersistenceService.saveExpression(expressionDto);
        return ResponseEntity.ok().body(new ExpressionResponse(expressionKey));
    }

    @PostMapping("/evaluate/{identifier}")
    public ResponseEntity<EvaluateResponse> evaluateExpression(@PathVariable String identifier, @RequestBody @Valid EvaluateRequest evaluateRequest) {
        EvaluateDto evaluateDto = expressionControllerMapper.mapToEvaluateDto(evaluateRequest, identifier);
        Boolean result = expressionEvaluatorService.evaluateExpression(evaluateDto);
        return ResponseEntity.ok().body(new EvaluateResponse(result));
    }
}