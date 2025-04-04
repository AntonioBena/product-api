package com.tis.interview.product.exception.global;

import com.tis.interview.product.exception.ExceptionResponse;
import com.tis.interview.product.exception.domain.ProductNotFoundException;
import com.tis.interview.product.exception.domain.ReviewNotFoundException;
import com.tis.interview.product.exception.security.RegisteredUserException;
import com.tis.interview.product.exception.security.UnauthorizedException;
import com.tis.interview.product.exception.security.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

import static com.tis.interview.product.exception.ErrorCodes.*;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler implements GlobalException {

    @Override
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException exp) {
        Set<String> errors = new HashSet<>();
        exp.getBindingResult().getAllErrors().forEach(error -> {
            var errorMessage = error.getDefaultMessage();
            errors.add(errorMessage);
        });
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .validationErrors(errors)
                                .build()
                );
    }

    @Override
    public ResponseEntity<ExceptionResponse> handleException(Exception exp) {
        exp.printStackTrace();
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .description("Internal error, please contact admin")
                                .error(exp.getMessage())
                                .build()
                );
    }

    @Override
    public ResponseEntity<ExceptionResponse> handleException(RegisteredUserException exp) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .error(exp.getMessage())
                                .build()
                );
    }

    @Override
    public ResponseEntity<ExceptionResponse> handleException(UnauthorizedException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .code(UNAUTHORIZED_ACCESS.getCode())
                                .description(UNAUTHORIZED_ACCESS.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @Override
    public ResponseEntity<ExceptionResponse> handleException(UserNotFoundException exp) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(
                        ExceptionResponse.builder()
                                .code(USER_NOT_FOUND.getCode())
                                .description(USER_NOT_FOUND.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @Override
    public ResponseEntity<ExceptionResponse> handleException(ProductNotFoundException exp) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(
                        ExceptionResponse.builder()
                                .code(PRODUCT_NOT_FOUND.getCode())
                                .description(PRODUCT_NOT_FOUND.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @Override
    public ResponseEntity<ExceptionResponse> handleException(ReviewNotFoundException exp) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(
                        ExceptionResponse.builder()
                                .code(REVIEW_NOT_FOUND.getCode())
                                .description(REVIEW_NOT_FOUND.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }


    //TODO implement custom exceptions!
}