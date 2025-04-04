package com.tis.interview.product.exception.security;

import com.tis.interview.product.exception.CustomException;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException(String message) {
        super(message);
    }
}