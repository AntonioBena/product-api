package com.tis.interview.product.exception.domain;

import com.tis.interview.product.exception.CustomException;

public class ExchangeNotFoundException extends CustomException {
    public ExchangeNotFoundException(String message) {
        super(message);
    }
}
