package com.hue.search.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ConstraintViolationException.class})
    public final ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> error : ex.getConstraintViolations()) {
            errors.put(error.getPropertyPath().toString().replace(".<list element>", ""), error.getMessage());
        }

        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ClientNotFoundException.class})
    public final ResponseEntity<Object> handleClientNotFoundException(ClientNotFoundException ex, WebRequest request) {
        var ApiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        return handleExceptionInternal(ex, ApiError, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ClientErrorException.class})
    public final ResponseEntity<Object> handleClientErrorException(ClientErrorException ex, WebRequest request) {
        var ApiError = new ApiError(ex.getStatus(), ex.getMessage());
        return handleExceptionInternal(ex, ApiError, new HttpHeaders(), ex.getStatus(), request);
    }

    @ExceptionHandler({ClientServerException.class})
    public final ResponseEntity<Object> handleClientServerException(ClientServerException ex, WebRequest request) {
        var ApiError = new ApiError(ex.getStatus(), ex.getMessage());
        return handleExceptionInternal(ex, ApiError, new HttpHeaders(), ex.getStatus(), request);
    }
}
