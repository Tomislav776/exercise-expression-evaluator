package hr.leapwise.expression.evaluator.core.service.logic;

import hr.leapwise.expression.evaluator.core.controller.exception.ExpressionOperatorNotValidException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum OperatorEnum {

    PARENTHESIS_OPEN("(", 6),
    PARENTHESIS_CLOSE(")", 6),
    NOT("!", 5),
    LESS("<", 4),
    LESS_EQUALS("<", 4),
    GREATER(">", 4),
    GREATER_EQUALS(">=", 4),
    EQUALS("==", 3),
    NOT_EQUALS("!=", 3),
    AND("&&", 2),
    OR("||", 1);

    private final String value;
    private final int precedence;


    OperatorEnum(String value, int precedence) {
        this.value = value;
        this.precedence = precedence;
    }

    public String getValue() {
        return value;
    }

    public int getPrecedence() {
        return precedence;
    }

    public static int getPrecedence(String value) {
        return Arrays.stream(OperatorEnum.values())
                .filter(operatorEnum -> operatorEnum.value.equals(value))
                .findFirst().orElseThrow(ExpressionOperatorNotValidException::new)
                .getPrecedence();
    }

    public static List<String> getOperators() {
        return Arrays.stream(OperatorEnum.values())
                .map(OperatorEnum::getValue)
                .collect(Collectors.toList());
    }

    public static boolean containsOperator(String operator) {
        return Arrays.stream(OperatorEnum.values())
                .anyMatch(operatorEnum -> operatorEnum.value.equals(operator));
    }
}
