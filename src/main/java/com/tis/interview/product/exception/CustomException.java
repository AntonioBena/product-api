package com.tis.interview.product.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}