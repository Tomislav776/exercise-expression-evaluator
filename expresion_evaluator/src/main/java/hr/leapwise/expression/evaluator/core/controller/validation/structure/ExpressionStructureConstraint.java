package hr.leapwise.expression.evaluator.core.controller.validation.structure;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({FIELD, METHOD, PARAMETER, TYPE_USE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ExpressionStructureValidator.class)
public @interface ExpressionStructureConstraint {
    String message() default "Invalid expression structure.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
