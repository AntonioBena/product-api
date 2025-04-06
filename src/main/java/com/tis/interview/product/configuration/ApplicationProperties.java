package com.tis.interview.product.configuration;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;

import java.util.List;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    @NotNull
    private final Hnb hnb;
    @NotNull
    private final Validation validation;
    @NotNull
    private final Security security;

    @Getter
    @AllArgsConstructor
    public static class Security {

        private final boolean createEnabledUsers;
        private final List<String> allowedHeaders;
        private final List<String> allowedOrigins;
        private final List<String> allowedMethods;
        private final List<String> requestMatchers;
        private final CorsConfiguration corsConfiguration;
        private final PasswordGenerator passwordGenerator;
        private Boolean csrfEnabled;

        public Customizer<CsrfConfigurer<HttpSecurity>> getCsrfConfig() {
            return csrfEnabled
                    ? Customizer.withDefaults()
                    : AbstractHttpConfigurer::disable;
        }

        public record PasswordGenerator(int passwordLength, PasswordLimits passwordLimits) {
            public record PasswordLimits(
                    @Min(48)
                    @Max(55)
                    int left,
                    @Min(100)
                    @Max(122)
                    int right)
            {
            }
        }

        public record CorsConfiguration(
                @NotBlank
                @Length(min = 3, message = "Cors pattern should be at least 3 chars long!")
                String pattern
        ) {}
    }

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

    @Getter
    @AllArgsConstructor
    public static class Validation {
        private String emailRegex;
        private String priceRegex;
    }
}