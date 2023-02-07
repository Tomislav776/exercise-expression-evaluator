package hr.leapwise.expression.evaluator.core.service.logic;

import hr.leapwise.expression.evaluator.core.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShuntingYardParserTest {

    @Mock
    private LogicalEvaluator logicalEvaluator;

    @InjectMocks
    private ShuntingYardParser shuntingYardParser;

    @Test
    void whenValidExpressionAndObject_thenReturnResult() {
        Map<String, Object> jsonObject = TestUtil.asJsonMap(getJsonFirst());
        String expression = "customer.firstName \"JOHN\" == customer.salary 100 < && customer.address null != customer.address.city \"Washington\" == && ||";

        when(logicalEvaluator.evaluateOperation(any(), any(), any())).thenReturn(true);
        Boolean actual = shuntingYardParser.evaluateExpression(jsonObject, Arrays.stream(expression.split(" ")).toList());
        assertTrue(actual);
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