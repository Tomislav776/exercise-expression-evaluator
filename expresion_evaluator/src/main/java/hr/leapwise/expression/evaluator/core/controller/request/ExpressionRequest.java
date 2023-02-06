package hr.leapwise.expression.evaluator.core.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record ExpressionRequest(@NotBlank(message = "Name can not be null.") String name,
                                @NotBlank(message = "Expression can not be null.") @Size(max = 1000, message = "Expression is too long.") String expression) implements Serializable {


}
