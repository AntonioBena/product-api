package com.tis.interview.product.exception.global;

import com.tis.interview.product.exception.ExceptionResponse;
import com.tis.interview.product.exception.security.RegisteredUserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public interface GlobalException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException exp);

    @ExceptionHandler(Exception.class)
    ResponseEntity<ExceptionResponse> handleException(Exception exp);

    @ExceptionHandler(RegisteredUserException.class)
    ResponseEntity<ExceptionResponse> handleException(RegisteredUserException exp);
}