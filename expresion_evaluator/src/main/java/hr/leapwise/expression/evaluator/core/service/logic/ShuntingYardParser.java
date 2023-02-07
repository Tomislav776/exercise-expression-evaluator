package hr.leapwise.expression.evaluator.core.service.logic;

import hr.leapwise.expression.evaluator.core.controller.exception.ExpressionStructureNotValidException;
import hr.leapwise.expression.evaluator.core.controller.exception.NotValueException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This is implementation of textbook ShuntingYard algorithm.
 */
@Component
@Slf4j
public class ShuntingYardParser {

    private final LogicalEvaluator logicalEvaluator;

    public ShuntingYardParser(LogicalEvaluator logicalEvaluator) {
        this.logicalEvaluator = logicalEvaluator;
    }

    public List<String> parseExpression(String expression) {
        expression = addSpaceAroundParanthesis(expression);
        Stack<String> operators = new Stack<>();
        Queue<String> output = new ArrayDeque<>();

        for (String token : tokenizeExpression(expression)) {
            if (token.equals("(")) {
                operators.push(token);
            } else if (token.equals(")")) {
                while (!operators.peek().equals("(")) {
                    output.add(operators.pop());
                }
                if (!operators.isEmpty() && !operators.peek().equals("(")) {
                    throw new ExpressionStructureNotValidException();
                } else {
                    operators.pop();
                }
            } else if (OperatorEnum.containsOperator(token)) {
                while (!operators.empty() && !"(".equals(operators.peek()) && OperatorEnum.getPrecedence(operators.peek()) >= OperatorEnum.getPrecedence(token)) {
                    output.add(operators.pop());
                }
                operators.push(token);
            } else {
                output.add(token);
            }
        }
        while (!operators.empty()) {
            output.add(operators.pop());
        }
        return output.stream().toList();
    }

    public Boolean evaluateExpression(Map<String, Object> json, List<String> tokens) {
        List<Object> infixTokens = replaceTokenParamWithValue(json, tokens);

        Stack<Object> stack = new Stack<>();
        List<String> operators = OperatorEnum.getOperators();

        for (Object token : infixTokens) {
            if (token == null || !operators.contains(token.toString())) {
                stack.push(token);
            } else {
                Object second = stack.pop();
                Object first = stack.pop();
                stack.push(logicalEvaluator.evaluateOperation(first, second, token.toString()));
            }
        }
        return (Boolean) stack.pop();
    }

    private List<Object> replaceTokenParamWithValue(Map<String, Object> json, List<String> infixTokens) {
        List<String> operators = OperatorEnum.getOperators();
        return infixTokens.stream()
                .map(token -> {
                    if (operators.contains(token)) {
                        return token;
                    }
                    try {
                        return castToValue(token);
                    } catch (NotValueException ignored) {
                    }
                    return extractValueFromJson(json, token);
                })
                .collect(Collectors.toList());
    }

    private Object castToValue(String token) throws NotValueException {
        if (token == null) {
            return null;
        }
        try {
            return Double.parseDouble(token);
        } catch (NumberFormatException exception) {
            log.trace(exception.getMessage(), exception);
        }
        if ("true".equalsIgnoreCase(token) || "false".equalsIgnoreCase(token)) {
            return Boolean.parseBoolean(token);
        }
        if (token.startsWith("\"") && token.endsWith("\"")) {
            return token.substring(1, token.length() - 1);
        }
        throw new NotValueException();
    }

    private Object extractValueFromJson(Map<String, Object> json, String parameter) {
        String[] keys = parameter.split("\\.");
        Object value = json;
        try {
            for (String key : keys) {
                if (json != null) {
                    value = ((Map<?, ?>) value).get(key);
                } else {
                    return null;
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
        return value;
    }

    private List<String> tokenizeExpression(String expression) {
        List<String> tokens = Arrays.stream(expression.split("\\s+")).toList();
        return tokens.stream()
                .filter(token -> !"".equals(token))
                .map(token -> switch (token.toUpperCase()) {
                    case "AND" -> "&&";
                    case "OR" -> "||";
                    case "NOT" -> "!";
                    case "EQ" -> "==";
                    default -> token;
                })
                .collect(Collectors.toList());
    }

    private String addSpaceAroundParanthesis(String expression) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < expression.length(); i++) {
            char character = expression.charAt(i);
            if (character == '(') {
                stringBuilder.append(" ( ");
            } else if (character == ')') {
                stringBuilder.append(" ) ");
            } else {
                stringBuilder.append(character);
            }
        }
        return stringBuilder.toString();
    }
}
