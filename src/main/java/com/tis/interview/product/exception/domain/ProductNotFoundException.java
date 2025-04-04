package com.tis.interview.product.exception.domain;

import com.tis.interview.product.exception.CustomException;

public class ProductNotFoundException extends CustomException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}