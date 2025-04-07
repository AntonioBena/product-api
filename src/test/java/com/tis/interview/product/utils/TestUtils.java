package com.tis.interview.product.utils;

import com.tis.interview.product.model.dto.ProductDto;
import com.tis.interview.product.model.dto.ReviewDto;
import com.tis.interview.product.model.dto.requests.AuthenticationRequest;
import com.tis.interview.product.model.dto.requests.UserRegistrationRequest;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

public final class TestUtils {
    public static String readJsonFile(String path) throws IOException {
        Path filePath = Path.of(path);
        return Files.readString(filePath);
    }

    public static ProductDto prepareProductDto(String code, String name, String desc, BigDecimal price) {
        return ProductDto.builder()
                .productCode(code)
                .productName(name)
                .productDescription(desc)
                .productPrice(price)
                .build();
    }

    public static ReviewDto prepareReviewDto(String text, ProductDto product, int rating) {
        return ReviewDto.builder()
                .text(text)
                .product(product)
                .rating(rating)
                .build();
    }

    public static AuthenticationRequest generateAuthRequest(String accId, String pass) {
        var auth = new AuthenticationRequest();
        auth.setAccountId(accId);
        auth.setPassword(pass);
        return auth;
    }

    public static UserRegistrationRequest generateUserRegistrationRequest(String accId, String fName, String lName) {
        var auth = new UserRegistrationRequest();
        auth.setAccountId(accId);
        auth.setFirstName(fName);
        auth.setLastName(lName);
        return auth;
    }
}