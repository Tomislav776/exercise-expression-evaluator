package hr.leapwise.expression.evaluator.core.controller;

import hr.leapwise.expression.evaluator.core.controller.mapper.ExpressionControllerMapper;
import hr.leapwise.expression.evaluator.core.controller.request.EvaluateRequest;
import hr.leapwise.expression.evaluator.core.controller.request.ExpressionRequest;
import hr.leapwise.expression.evaluator.core.persistence.repository.ExpressionRepository;
import hr.leapwise.expression.evaluator.core.service.ExpressionEvaluatorService;
import hr.leapwise.expression.evaluator.core.service.ExpressionPersistenceService;
import hr.leapwise.expression.evaluator.core.stub.EvaluateRequestGenerator;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static hr.leapwise.expression.evaluator.core.util.TestUtil.asJsonString;

@RunWith(SpringRunner.class)
@WebMvcTest(ExpressionController.class)
@AutoConfigureMockMvc
class ExpressionControllerTest {

    @MockBean
    private ExpressionRepository expressionRepository;
    @MockBean
    private ExpressionControllerMapper expressionControllerMapper;
    @MockBean
    private ExpressionPersistenceService expressionPersistenceService;
    @MockBean
    private ExpressionEvaluatorService expressionEvaluatorService;

    @Autowired
    ExpressionController expressionController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenPostRequestToExpressionAndValidExpression_thenCorrectResponse() throws Exception {
        ExpressionRequest expressionRequest = new ExpressionRequest("Test expression",
                "(customer.firstName == \"JOHN\" && customer.salary < 100) OR (customer.address != null && customer.address.city == \"Washington\")");
        mockMvc.perform(MockMvcRequestBuilders.post("/expression")
                        .content(asJsonString(expressionRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenPostRequestToExpressionAndParamIsNull_thenBadRequestResponse() throws Exception {
        ExpressionRequest expressionRequestNameNull = new ExpressionRequest(null,
                "(customer.firstName == \"JOHN\" && customer.salary < 100) OR (customer.address != null && customer.address.city == \"Washington\")");
        ExpressionRequest expressionRequestExpressionNull = new ExpressionRequest("Test expression",
                null);
        mockMvc.perform(MockMvcRequestBuilders.post("/expression")
                        .content(asJsonString(expressionRequestNameNull))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders.post("/expression")
                        .content(asJsonString(expressionRequestExpressionNull))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenPostRequestToExpressionAndNotValidStructureExpression_thenBadRequestResponse() throws Exception {
        ExpressionRequest expressionRequest = new ExpressionRequest("Test expression",
                "(customer.firstName == \"JOHN\" && customer.salary < 100) OR OR (customer.address != null && customer.address.city == \"Washington\")");
        mockMvc.perform(MockMvcRequestBuilders.post("/expression")
                        .content(asJsonString(expressionRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenPostRequestToExpressionAndNotValidOperatorExpression_thenBadRequestResponse() throws Exception {
        ExpressionRequest expressionRequest = new ExpressionRequest("Test expression",
                "(customer.firstName == \"JOHN\" && customer.salary < 100) oR (customer.address != null && customer.address.city == \"Washington\")");
        mockMvc.perform(MockMvcRequestBuilders.post("/expression")
                        .content(asJsonString(expressionRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenPostRequestToEvaluateAndValidRequest_thenCorrectResponse() throws Exception {
        EvaluateRequest evaluateRequest = EvaluateRequestGenerator.getEvaluateRequest();

        mockMvc.perform(MockMvcRequestBuilders.post("/evaluate/9f9001ef-555e-4b91-99da-97c64fcce947")
                        .content(asJsonString(evaluateRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenPostRequestToEvaluateAndNullObject_thenBadRequestResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/evaluate/9f9001ef-555e-4b91-99da-97c64fcce947")
                        .content(asJsonString(new EvaluateRequest(null)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }
}