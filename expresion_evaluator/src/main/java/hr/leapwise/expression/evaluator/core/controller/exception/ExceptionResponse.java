package hr.leapwise.expression.evaluator.core.controller.exception;

import java.time.LocalDateTime;

public record ExceptionResponse(LocalDateTime localDateTime, String message) {

}
