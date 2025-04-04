package com.tis.interview.product.exception.global;

import com.tis.interview.product.exception.ExceptionResponse;
import com.tis.interview.product.exception.domain.ProductNotFoundException;
import com.tis.interview.product.exception.domain.ReviewNotFoundException;
import com.tis.interview.product.exception.security.RegisteredUserException;
import com.tis.interview.product.exception.security.UnauthorizedException;
import com.tis.interview.product.exception.security.UserNotFoundException;
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

    @ExceptionHandler(UnauthorizedException.class)
    ResponseEntity<ExceptionResponse> handleException(UnauthorizedException exp);

    @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<ExceptionResponse> handleException(UserNotFoundException exp);

    @ExceptionHandler(ProductNotFoundException.class)
    ResponseEntity<ExceptionResponse> handleException(ProductNotFoundException exp);

    @ExceptionHandler(ReviewNotFoundException.class)
    ResponseEntity<ExceptionResponse> handleException(ReviewNotFoundException exp);
}