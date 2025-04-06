package com.tis.interview.product.service.security;

import com.tis.interview.product.model.dto.requests.AuthenticationRequest;
import com.tis.interview.product.model.dto.requests.UserRegistrationRequest;
import com.tis.interview.product.model.dto.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Override
    public AuthResponse<?> registerNewUser(UserRegistrationRequest request) throws Exception {
        return null;
    }

    @Override
    public AuthResponse<?> authenticate(AuthenticationRequest request) throws Exception {
        return null;
    }
}
