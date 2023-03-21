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
        String message = "요청값이 제약조건에 위배됩니다.";
        log.debug("warn come here");

        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> error : ex.getConstraintViolations()) {
            errors.put(error.getPropertyPath().toString().replace(".<list element>", ""), error.getMessage());
        }

//        List<String> messages = new ArrayList<>();
//        for (ConstraintViolation<?> error : ex.getConstraintViolations()) {
//            messages.add(String.format("%s: %s", parseParameterSimpleName(error), error.getMessage()));
//        }

//        var errorResponseBody = new ErrorResponseBody(stayErrorStatus.errorCode, String.join(", ", messages));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({Exception.class})
    public final ResponseEntity<Object> handleConstraintViolationException2(Exception ex, WebRequest request) {
        String message = "요청값이 제약조건에 위배됩니다.";

        log.debug("warn come here22");
        log.debug("ex: {}", ex);

//        Map<String, String> errors = new HashMap<>();
//        for (ConstraintViolation<?> error : ex.getConstraintViolations()) {
//            errors.put(error.getPropertyPath().toString().replace(".<list element>", ""), error.getMessage());
//        }

//        List<String> messages = new ArrayList<>();
//        for (ConstraintViolation<?> error : ex.getConstraintViolations()) {
//            messages.add(String.format("%s: %s", parseParameterSimpleName(error), error.getMessage()));
//        }

//        var errorResponseBody = new ErrorResponseBody(stayErrorStatus.errorCode, String.join(", ", messages));
        return handleExceptionInternal(ex, "test", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

//    @ExceptionHandler({Exception.class})
//    public final ResponseEntity<Object> handleConstraintViolationException3(Exception ex, WebRequest request) {
//        String message = "요청값이 제약조건에 위배됩니다.";
//
//        log.debug("warn come here22");
//        log.debug("ex: {}", ex);
//
////        Map<String, String> errors = new HashMap<>();
////        for (ConstraintViolation<?> error : ex.getConstraintViolations()) {
////            errors.put(error.getPropertyPath().toString().replace(".<list element>", ""), error.getMessage());
////        }
//
////        List<String> messages = new ArrayList<>();
////        for (ConstraintViolation<?> error : ex.getConstraintViolations()) {
////            messages.add(String.format("%s: %s", parseParameterSimpleName(error), error.getMessage()));
////        }
//
////        var errorResponseBody = new ErrorResponseBody(stayErrorStatus.errorCode, String.join(", ", messages));
//        return handleExceptionInternal(ex, "test", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
//    }
}
