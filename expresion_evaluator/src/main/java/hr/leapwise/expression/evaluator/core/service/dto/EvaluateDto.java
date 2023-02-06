package hr.leapwise.expression.evaluator.core.service.dto;

import java.util.Map;

public record EvaluateDto(String identifier, Map<String, Object> json) {
}
