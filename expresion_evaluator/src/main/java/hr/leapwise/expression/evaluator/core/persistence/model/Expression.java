package hr.leapwise.expression.evaluator.core.persistence.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "expression")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Expression implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "evaluator_expression_seq")
    @SequenceGenerator(name = "evaluator_expression_seq", sequenceName = "EVALUATOR_EXPRESSIONS_SEQ", allocationSize = 1)
    private Long id;

    private String identifier = UUID.randomUUID().toString();

    private String name;

    private String expression;

    public Expression(String name, String expression) {
        this.name = name;
        this.expression = expression;
    }

    public Expression(String identifier, String name, String expression) {
        this.identifier = identifier;
        this.name = name;
        this.expression = expression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expression that = (Expression) o;
        return id.equals(that.id) && identifier.equals(that.identifier) && name.equals(that.name) && expression.equals(that.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, identifier, name, expression);
    }
}
