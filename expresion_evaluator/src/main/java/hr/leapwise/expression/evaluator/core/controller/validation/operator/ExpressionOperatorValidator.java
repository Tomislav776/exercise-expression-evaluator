package hr.leapwise.expression.evaluator.core.controller.validation.operator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExpressionOperatorValidator implements ConstraintValidator<ExpressionOperatorConstraint, String> {

    @Override
    public boolean isValid(String expressionToValidate, ConstraintValidatorContext context) {
        //ToDo: implement
        return true;
    }

}
