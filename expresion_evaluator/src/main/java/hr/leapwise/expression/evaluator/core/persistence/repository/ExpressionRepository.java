package hr.leapwise.expression.evaluator.core.persistence.repository;

import hr.leapwise.expression.evaluator.core.persistence.model.Expression;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpressionRepository extends JpaRepository<Expression, Long> {

    Optional<Expression> findByIdentifier(String identifier);

}
