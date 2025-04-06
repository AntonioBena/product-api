package com.tis.interview.product.controller;

import com.tis.interview.product.model.dto.requests.AuthenticationRequest;
import com.tis.interview.product.model.dto.requests.UserRegistrationRequest;
import com.tis.interview.product.model.dto.response.AuthResponse;
import com.tis.interview.product.service.security.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication")
@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AuthenticationController {
    private final AuthenticationService authService;

    @Operation(
            description = "Endpoint for creating User account",
            summary = "Creates user account and returns response with generated plaintext password"
    )
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AuthResponse<Object> createAccount(@RequestBody @Valid UserRegistrationRequest request) {
        return authService.registerNewUser(request);
    }

    @Operation(
            description = "Endpoint for user login",
            summary = "Logs in user"
    )
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AuthResponse<Object> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        return authService.authenticate(request);
    }
}