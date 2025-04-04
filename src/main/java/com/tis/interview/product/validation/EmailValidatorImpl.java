package com.tis.interview.product.validation;

import com.tis.interview.product.configuration.ApplicationProperties;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.regex.Pattern;

@RequiredArgsConstructor
public class EmailValidatorImpl implements ConstraintValidator<EmailValidator, String> {
    private final ApplicationProperties appProperties;

    @Override
    public boolean isValid(String emailField, ConstraintValidatorContext cxt) {
        Pattern pattern = Pattern.compile(
                appProperties
                        .getValidation()
                        .getEmailRegex()
        );
        return emailField != null && pattern.matcher(emailField).matches();
    }
}