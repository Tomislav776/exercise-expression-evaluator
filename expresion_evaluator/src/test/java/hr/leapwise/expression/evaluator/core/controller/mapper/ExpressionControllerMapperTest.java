package hr.leapwise.expression.evaluator.core.controller.mapper;

import hr.leapwise.expression.evaluator.core.controller.request.ExpressionRequest;
import hr.leapwise.expression.evaluator.core.service.dto.ExpressionDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ExpressionControllerMapperTest {

    @InjectMocks
    private ExpressionControllerMapper expressionControllerMapper;

    @Test
    void whenValidExpression_thenTransformToUniformExpression() {
        ExpressionRequest expressionRequest = new ExpressionRequest("Test expression",
                "(customer.firstName == \"JOHN\" && customer.salary < 100) OR (customer.address != null && customer.address.city == \"Washington\")");

        ExpressionDto actual = expressionControllerMapper.mapToExpressionDto(expressionRequest);

        ExpressionDto expressionDtoExpected = new ExpressionDto("Test expression",
                "(customer.firstName == \"JOHN\" && customer.salary < 100) || (customer.address != null && customer.address.city == \"Washington\")");

        assertEquals(expressionDtoExpected, actual);
    }


}