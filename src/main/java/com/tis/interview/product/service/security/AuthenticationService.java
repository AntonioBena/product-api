package com.tis.interview.product.service.security;

import com.tis.interview.product.model.dto.requests.AuthenticationRequest;
import com.tis.interview.product.model.dto.requests.UserRegistrationRequest;
import com.tis.interview.product.model.dto.response.AuthResponse;

public interface AuthenticationService {
    AuthResponse<Object> registerNewUser(UserRegistrationRequest request);

    AuthResponse<Object> authenticate(AuthenticationRequest request);
}