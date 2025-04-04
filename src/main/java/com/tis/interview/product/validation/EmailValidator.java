package com.tis.interview.product.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailValidatorImpl.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailValidator {
    String message() default "Email Malformed!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}