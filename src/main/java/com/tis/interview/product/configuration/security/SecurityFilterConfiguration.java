package com.tis.interview.product.configuration.security;

import com.tis.interview.product.configuration.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Log4j2
@Configuration
@RequiredArgsConstructor
public class SecurityFilterConfiguration {
    private final ApplicationProperties applicationProperties;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(
                        applicationProperties
                                .getSecurity()
                                .getCsrfConfig()
                )
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(
                                        applicationProperties.getSecurity()
                                                .getRequestMatchers()
                                                .toArray(new String[0]))
                                .permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = getCorsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source
                .registerCorsConfiguration(
                        applicationProperties
                                .getSecurity()
                                .getCorsConfiguration()
                                .pattern(),
                        configuration);
        return source;
    }

    private CorsConfiguration getCorsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
                applicationProperties
                        .getSecurity()
                        .getAllowedOrigins()
        );
        configuration.setAllowedMethods(
                applicationProperties
                        .getSecurity()
                        .getAllowedMethods()
        );
        configuration.setAllowedHeaders(
                applicationProperties
                        .getSecurity()
                        .getAllowedHeaders()
        );
        configuration.setAllowCredentials(true);
        return configuration;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}