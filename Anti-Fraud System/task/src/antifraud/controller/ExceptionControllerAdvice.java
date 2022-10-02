package antifraud.controller;


import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler({ConstraintViolationException.class, InvalidDataAccessApiUsageException.class})
    ResponseEntity<String> exceptionHandler400(RuntimeException e) {
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }

}
