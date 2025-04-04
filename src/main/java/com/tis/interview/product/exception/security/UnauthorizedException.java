package com.tis.interview.product.exception.security;

import com.tis.interview.product.exception.CustomException;

public class UnauthorizedException extends CustomException {
    public UnauthorizedException(String message) {
        super(message);
    }
}