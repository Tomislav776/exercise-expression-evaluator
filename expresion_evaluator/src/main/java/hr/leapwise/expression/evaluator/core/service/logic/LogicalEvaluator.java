package hr.leapwise.expression.evaluator.core.service.logic;

import hr.leapwise.expression.evaluator.core.controller.exception.ValueCanNotBeNullException;
import hr.leapwise.expression.evaluator.core.controller.exception.ValueIsNotNumberException;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;
import java.util.function.Function;

@Component
public class LogicalEvaluator {

    public boolean evaluateOperation(Object first, Object second, String operation)  {
        return switch (operation) {
            case "&&" -> evaluateAnd.apply(first, second);
            case "||" -> evaluateOr.apply(first, second);
            case "<" -> evaluateLess.apply(first, second);
            case ">" -> evaluateGreater.apply(first, second);
            case "<=" -> evaluateLessEquals.apply(first, second);
            case ">=" -> evaluateGreaterEquals.apply(first, second);
            case "==" -> evaluateEquals.apply(first, second);
            case "!=" -> evaluateNotEquals.apply(first, second);
            case "!" -> evaluateNot.apply(first);
            default -> false;
        };
    }

    //Logical operators
    private final BiFunction<Object, Object, Boolean> evaluateAnd = (first, second) -> getBooleanValue(first) && getBooleanValue(second);
    private final BiFunction<Object, Object, Boolean> evaluateOr = (first, second) -> getBooleanValue(first) || getBooleanValue(second);
    private final Function<Object, Boolean> evaluateNot = (first) -> !getBooleanValue(first);

    //Comparison operators
    private final BiFunction<Object, Object, Boolean> evaluateEquals = Object::equals;
    private final BiFunction<Object, Object, Boolean> evaluateNotEquals = (first, second) -> !first.equals(second);

    private final BiFunction<Object, Object, Boolean> evaluateGreater = (first, second) -> getNumberValue(first) > getNumberValue(second);
    private final BiFunction<Object, Object, Boolean> evaluateGreaterEquals = (first, second) -> getNumberValue(first) >= getNumberValue(second);
    private final BiFunction<Object, Object, Boolean> evaluateLess = (first, second) -> getNumberValue(first) < getNumberValue(second);
    private final BiFunction<Object, Object, Boolean> evaluateLessEquals = (first, second) -> getNumberValue(first) <= getNumberValue(second);

    private boolean getBooleanValue(Object object) {
        if (object == null) {
            throw new ValueCanNotBeNullException();
        }
        if (object instanceof Number) {
            return 0 != ((Number) object).intValue();
        }
        if (object instanceof Boolean) {
            return (Boolean) object;
        }
        if (object instanceof String) {
            if ("NULL".equalsIgnoreCase(((String) object))) {
                return false;
            }
            return !"".equals(object);
        }

        return true;
    }

    private Double getNumberValue(Object object) {
        if (object == null) {
            throw new ValueCanNotBeNullException();
        } else if (object instanceof Number) {
            return ((Number) object).doubleValue();
        } else {
            throw new ValueIsNotNumberException();
        }
    }

}
