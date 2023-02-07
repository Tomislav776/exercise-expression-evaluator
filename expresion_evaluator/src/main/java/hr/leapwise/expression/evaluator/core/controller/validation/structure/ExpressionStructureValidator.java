package hr.leapwise.expression.evaluator.core.controller.validation.structure;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExpressionStructureValidator implements ConstraintValidator<ExpressionStructureConstraint, String> {

    @Override
    public boolean isValid(String expression, ConstraintValidatorContext context) {
        //ToDo: implement
        return true;
    }


}
