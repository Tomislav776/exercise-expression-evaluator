package hr.leapwise.expression.evaluator.core.service.integration;

import hr.leapwise.expression.evaluator.core.persistence.model.Expression;
import hr.leapwise.expression.evaluator.core.persistence.repository.ExpressionRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ExpressionConfiguration {

    protected static final String UUID_EXPRESSION_1 = "9f9001ef-555e-4b91-99da-97c64fcce947";
    protected static final String UUID_EXPRESSION_2 = "9f9001ef-555e-4b91-99da-97c64fcce948";

    @Bean
    ApplicationRunner reportInit(ExpressionRepository expressionRepository) {
        return args -> {
            expressionRepository.save(new Expression(1L, UUID_EXPRESSION_1, "First expression",
                    "(customer.firstName == \"JOHN\" && customer.salary < 100) || (customer.address != null && customer.address.city == \"Washington\")"));
            expressionRepository.save(new Expression(2L, UUID_EXPRESSION_2, "Second expression",
                    "customer.firstName == \"JOHN\" && customer.salary < 100"));
        };
    }

}
