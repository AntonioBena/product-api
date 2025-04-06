package com.tis.interview.product.controller;

import com.tis.interview.product.model.dto.requests.AuthenticationRequest;
import com.tis.interview.product.model.dto.requests.UserRegistrationRequest;
import com.tis.interview.product.model.dto.response.AuthResponse;
import com.tis.interview.product.service.security.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AuthenticationController {
    private final AuthenticationService authService;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AuthResponse<Object> createAccount(@RequestBody @Valid UserRegistrationRequest request) {
        return authService.registerNewUser(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AuthResponse<Object> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        return authService.authenticate(request);
    }
}