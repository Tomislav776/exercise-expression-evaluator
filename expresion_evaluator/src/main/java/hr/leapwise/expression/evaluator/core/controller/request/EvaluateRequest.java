package hr.leapwise.expression.evaluator.core.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Map;

public record EvaluateRequest(@NotNull(message = "JSON can not be null.") @Size(max = 10000, message = "Expression is too long.") Map<String, Object> json) {
}
