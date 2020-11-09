package com.golden.demo.enrollment.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.golden.demo.exception.PersonNotFoundException;

@ControllerAdvice
public class PersonExceptionHandler extends ResponseEntityExceptionHandler {
 
    @ExceptionHandler({ PersonNotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Person not found", new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    
    
    @ExceptionHandler({ Exception.class })
    protected ResponseEntity<Object> exceptionHandler(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Unhandled exception", new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}