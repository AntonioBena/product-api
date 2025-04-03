package com.tis.interview.product.configuration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    @NotNull
    private final Hnb hnb;

    @Getter
    @AllArgsConstructor
    public static class Hnb {
        @NotBlank(message = "Api url is mandatory!")
        private String apiUrl;
        @NotBlank(message = "Currency uri is mandatory!")
        private String currencyUri;
        @NotBlank(message = "currency is mandatory!")
        private String currency;

    }
}