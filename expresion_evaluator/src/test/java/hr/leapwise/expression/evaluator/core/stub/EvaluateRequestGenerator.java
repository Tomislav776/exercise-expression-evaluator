package hr.leapwise.expression.evaluator.core.stub;

import hr.leapwise.expression.evaluator.core.controller.request.EvaluateRequest;
import hr.leapwise.expression.evaluator.core.util.TestUtil;

public class EvaluateRequestGenerator {

    public static EvaluateRequest getEvaluateRequest() {
        String json = """
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
                }""";

        return new EvaluateRequest(
                TestUtil.asJsonMap(json)
        );
    }

}
