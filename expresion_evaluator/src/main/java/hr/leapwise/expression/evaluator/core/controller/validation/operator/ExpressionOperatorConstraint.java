package hr.leapwise.expression.evaluator.core.controller.validation.operator;

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
@Constraint(validatedBy = ExpressionOperatorValidator.class)
public @interface ExpressionOperatorConstraint {
    String message() default "Expression uses invalid operators.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
