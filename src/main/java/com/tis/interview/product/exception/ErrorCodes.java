package com.tis.interview.product.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCodes {
    NO_CODE(0, NOT_IMPLEMENTED, "No code"),
    ACCOUNT_LOCKED(302, FORBIDDEN, "User account is locked"),
    UNAUTHORIZED_ACCESS(401, UNAUTHORIZED, "User can not access resource"),
    USER_NOT_FOUND(404, NOT_FOUND, "User not found"),
    PRODUCT_NOT_FOUND(404, NOT_FOUND, "Product not found"),
    REVIEW_NOT_FOUND(404, NOT_FOUND, "Review not found"),
    INCORRECT_CURRENT_PASSWORD(300, BAD_REQUEST, "Incorrect password"),
    PASSWORD_DOES_NOT_MATCH(301, BAD_REQUEST, "The new password does not match"),
    ACCOUNT_DISABLED(303, FORBIDDEN, "Account disabled"),
    BAD_CREDENTIALS(304, FORBIDDEN, "Email and / or password is incorrect"),
    ;
    private final int code;
    private final String description;
    private final HttpStatus httpStatus;

    ErrorCodes(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}