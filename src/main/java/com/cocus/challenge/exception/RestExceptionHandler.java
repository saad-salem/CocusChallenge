package com.cocus.challenge.exception;

import com.cocus.challenge.util.InfraUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;

@RestControllerAdvice
public class RestExceptionHandler {
    /**
     * NotAcceptableHeaderException handler method
     */
    @ExceptionHandler(value = {NotAcceptableHeaderException.class})
    public ResponseEntity<ErrorDto> handle(NotAcceptableHeaderException ex) {
        ErrorDto res = new ErrorDto(HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(res);
    }

    /**
     * NotFoundException handler method
     */
    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ErrorDto> handle(NotFoundException ex) {
        ErrorDto res = new ErrorDto(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    /**
     * RateLimitException handler method
     */
    @ExceptionHandler(value = {RateLimitExceedException.class})
    public ResponseEntity<ErrorDto> handle(RateLimitExceedException ex) {
        ErrorDto res = new ErrorDto(HttpStatus.FORBIDDEN.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(res);
    }

    /**
     * internalServerError handler method
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> processException(Exception ex) {
        return new ResponseEntity<>(new GeneralErrorDto(
                new Date(),
                "internal",
                ex.getMessage(),
                new HashMap<>(),
                InfraUtils.getStackTrace(ex)
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
