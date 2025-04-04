package com.tis.interview.product.exception.security;

import com.tis.interview.product.exception.CustomException;

public class RegisteredUserException extends CustomException {
    public RegisteredUserException(String message) {
        super(message);
    }
}