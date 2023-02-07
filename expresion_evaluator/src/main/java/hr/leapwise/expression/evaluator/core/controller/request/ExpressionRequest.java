package hr.leapwise.expression.evaluator.core.controller.request;

import hr.leapwise.expression.evaluator.core.controller.validation.operator.ExpressionOperatorConstraint;
import hr.leapwise.expression.evaluator.core.controller.validation.structure.ExpressionStructureConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record ExpressionRequest(@NotBlank(message = "Name can not be null.")
                                @Size(max = 254, message = "Name is too long.") String name,
                                @NotBlank(message = "Expression can not be null.")
                                @Size(max = 1000, message = "Expression is too long.")
                                @ExpressionOperatorConstraint
                                @ExpressionStructureConstraint String expression) implements Serializable {
}
