package com.tis.interview.product.exception.domain;

import com.tis.interview.product.exception.CustomException;

public class ReviewNotFoundException extends CustomException {
    public ReviewNotFoundException(String message) {
        super(message);
    }
}