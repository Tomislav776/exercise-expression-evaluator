package hr.leapwise.expression.evaluator.core.service;

import hr.leapwise.expression.evaluator.core.service.dto.EvaluateDto;
import hr.leapwise.expression.evaluator.core.service.dto.ExpressionDto;
import hr.leapwise.expression.evaluator.core.service.integration.ExpressionEvaluatorServiceImpl;
import hr.leapwise.expression.evaluator.core.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpressionEvaluatorServiceTest {

    private static final String IDENTIFIER = "9f9001ef-555e-4b91-99da-97c64fcce947";

    @Mock
    private ExpressionPersistenceService expressionPersistenceService;


    @InjectMocks
    private ExpressionEvaluatorServiceImpl expressionEvaluatorService;

    @Test
    void whenValidExpressionAndObject_thenReturnResult() {
        Map<String, Object> jsonObject = TestUtil.asJsonMap(getJsonFirst());
        EvaluateDto evaluateDto = new EvaluateDto(IDENTIFIER, jsonObject);
        ExpressionDto expressionDto = new ExpressionDto("Test",
                "(customer.firstName == \"JOHN\" && customer.salary < 100) OR (customer.address != null && customer.address.city == \"Washington\")");

        when(expressionPersistenceService.findByIdentifier(any())).thenReturn(expressionDto);

        Boolean actual = expressionEvaluatorService.evaluateExpression(evaluateDto);
        assertTrue(actual);
    }

    @Test
    void whenValidExpressionAndObjectWithMissingParams_thenReturnResult() {
        Map<String, Object> jsonObject = TestUtil.asJsonMap(getJsonNoFirstName());
        EvaluateDto evaluateDto = new EvaluateDto(IDENTIFIER, jsonObject);
        ExpressionDto expressionDto = new ExpressionDto("Test",
                "(customer.firstName == \"JOHN\" && customer.salary < 100) OR (customer.address != null && customer.address.city == \"Washington\")");

        when(expressionPersistenceService.findByIdentifier(any())).thenReturn(expressionDto);

        Boolean actual = expressionEvaluatorService.evaluateExpression(evaluateDto);
        assertFalse(actual);
    }

    private static String getJsonFirst() {
        return """
                {
                  "customer":
                  {
                    "firstName": "JOHN",
                    "lastName": "DOE",\s
                    "address":
                    {
                      "city": "Chicago",
                      "zipCode": 1234,\s
                      "street": "56th",\s
                      "houseNumber": 2345
                    },
                    "salary": 99,
                    "type": "BUSINESS"
                  }
                }
                """;
    }

    private static String getJsonNoFirstName() {
        return """
                {
                  "customer":
                  {
                    "lastName": "DOE",\s
                    "address":
                    {
                      "city": "Chicago",
                      "zipCode": 1234,\s
                      "street": "56th",\s
                      "houseNumber": 2345
                    },
                    "salary": 99,
                    "type": "BUSINESS"
                  }
                }
                """;
    }

}