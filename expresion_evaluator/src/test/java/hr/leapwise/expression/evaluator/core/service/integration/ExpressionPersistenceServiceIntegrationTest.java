package hr.leapwise.expression.evaluator.core.service.integration;

import hr.leapwise.expression.evaluator.core.controller.exception.ExpressionNotFoundException;
import hr.leapwise.expression.evaluator.core.service.ExpressionPersistenceService;
import hr.leapwise.expression.evaluator.core.service.dto.ExpressionDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static hr.leapwise.expression.evaluator.core.service.integration.ExpressionConfiguration.UUID_EXPRESSION_1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ExpressionConfiguration.class)
class ExpressionPersistenceServiceIntegrationTest {

    @Autowired
    private ExpressionPersistenceService expressionPersistenceService;

    @Test
    void whenFindExpressionAndExistingUUID_thenReturnExpression() {
        ExpressionDto expression = expressionPersistenceService.findByIdentifier(UUID_EXPRESSION_1);
        ExpressionDto expressionExpected = new ExpressionDto(1L, UUID_EXPRESSION_1, "First expression",
                "(customer.firstName == \"JOHN\" && customer.salary < 100) || (customer.address != null && customer.address.city == \"Washington\")");

        assertEquals(expressionExpected, expression);
    }

    @Test
    void whenFindExpressionAndNonExistingUUID_thenThrowException() {
        Assertions.assertThrows(ExpressionNotFoundException.class, () -> expressionPersistenceService.findByIdentifier("TEST"));
    }

    @Test
    void whenSaveAndFindExpression_thenReturnExpression() {
        ExpressionDto expressionDto = new ExpressionDto("First expression",
                "(customer.firstName == \"JOHN\" && customer.salary < 100) || (customer.address != null && customer.address.city == \"Washington\")");

        String identifierActual = expressionPersistenceService.saveExpression(expressionDto);
        ExpressionDto expressionActual = expressionPersistenceService.findByIdentifier(identifierActual);

        assertNotNull(identifierActual);
        assertNotNull(expressionActual);
    }
}