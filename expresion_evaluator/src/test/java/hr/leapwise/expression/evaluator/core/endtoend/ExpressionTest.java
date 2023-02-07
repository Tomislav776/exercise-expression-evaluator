package hr.leapwise.expression.evaluator.core.endtoend;

import hr.leapwise.expression.evaluator.core.ExpressionEvaluatorApplication;
import hr.leapwise.expression.evaluator.core.controller.ExpressionController;
import hr.leapwise.expression.evaluator.core.controller.mapper.ExpressionControllerMapper;
import hr.leapwise.expression.evaluator.core.controller.request.EvaluateRequest;
import hr.leapwise.expression.evaluator.core.controller.request.ExpressionRequest;
import hr.leapwise.expression.evaluator.core.persistence.repository.ExpressionRepository;
import hr.leapwise.expression.evaluator.core.service.ExpressionEvaluatorService;
import hr.leapwise.expression.evaluator.core.service.ExpressionPersistenceService;
import hr.leapwise.expression.evaluator.core.service.logic.ShuntingYardParser;
import hr.leapwise.expression.evaluator.core.stub.EvaluateRequestGenerator;
import hr.leapwise.expression.evaluator.core.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

import static hr.leapwise.expression.evaluator.core.util.TestUtil.asJsonString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ExpressionEvaluatorApplication.class)
@AutoConfigureMockMvc
class ExpressionTest {

    @Autowired
    private ExpressionRepository expressionRepository;
    @Autowired
    private ExpressionControllerMapper expressionControllerMapper;
    @Autowired
    private ExpressionPersistenceService expressionPersistenceService;
    @Autowired
    private ExpressionEvaluatorService expressionEvaluatorService;
    @Autowired
    private ShuntingYardParser shuntingYardParser;

    @Autowired
    ExpressionController expressionController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createAndEvaluateExpression_happyDay() throws Exception {
        ExpressionRequest expressionRequest = new ExpressionRequest("Test expression",
                "(customer.firstName == \"JOHN\" && customer.salary < 100) OR (customer.address != null && customer.address.city == \"Washington\")");

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/expression")
                        .content(asJsonString(expressionRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));

        String responseJson = resultActions.andReturn().getResponse().getContentAsString();
        Map<String, Object> responseMap = TestUtil.asJsonMap(responseJson);

        String identifier = (String) responseMap.get("identifier");
        EvaluateRequest evaluateRequest = EvaluateRequestGenerator.getEvaluateRequest();
        mockMvc.perform(MockMvcRequestBuilders.post("/evaluate/" + identifier)
                        .content(asJsonString(evaluateRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value(true));
    }
}