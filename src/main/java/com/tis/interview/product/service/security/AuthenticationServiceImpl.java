package com.tis.interview.product.service.security;

import com.tis.interview.product.configuration.ApplicationProperties;
import com.tis.interview.product.exception.security.RegisteredUserException;
import com.tis.interview.product.model.UserEntity;
import com.tis.interview.product.model.dto.UserDto;
import com.tis.interview.product.model.dto.requests.AuthenticationRequest;
import com.tis.interview.product.model.dto.requests.UserRegistrationRequest;
import com.tis.interview.product.model.dto.response.AuthResponse;
import com.tis.interview.product.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

import static com.tis.interview.product.model.UserRole.USER;

@Log4j2
@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationProperties appProperties;
    private final AuthenticationManager authenticationManager;
    private final Random random = new Random();

    @Override
    public AuthResponse<Object> registerNewUser(UserRegistrationRequest request) {
        log.info("Registering new user with request: {}", request);
        if (userRepository.existsByEmail(request.getAccountId())) {
            log.error("User with email {} already exists", request.getAccountId());
            throw new RegisteredUserException("User already registered!");
        }

        var plainPassword = generatePlainTextPassword();
        var newAccount = buildNewUserAccount(request, plainPassword);

        userRepository.save(newAccount);
        log.info("Account created: {}", newAccount);
        return AuthResponse.builder()
                .success(true)
                .description("Account created!")
                .password(plainPassword)
                .build();
    }

    @Override
    public AuthResponse<Object> authenticate(AuthenticationRequest request) {
        log.info("Authenticate request: {}", request);
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getAccountId(), request.getPassword()));
        } catch (AuthenticationException exception) {
            log.error("Authentication failed, bad credentials: {}", exception.getMessage());
            throw new BadCredentialsException("Bad credentials", exception);
        }

        log.info("User authenticated successfully! {}", authentication.getPrincipal());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var authenticatedUserDto = revealAuthenticatedUserAndMapToDto(authentication);

        return AuthResponse.builder()
                .success(true)
                .description("User authenticated successfully")
                .data(authenticatedUserDto)
                .build();
    }

    private UserDto revealAuthenticatedUserAndMapToDto(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        var foundUser = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Can not find logged in user: " + userDetails.getUsername()));

        return buildUserDto(foundUser);
    }

    private UserDto buildUserDto(UserEntity user) {
        return UserDto.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .createdAt(user.getCreatedAt())
                .build();
    }

    private UserEntity buildNewUserAccount(UserRegistrationRequest request, String plainPassword) {
        return UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getAccountId())
                .password(
                        passwordEncoder.encode(plainPassword)
                )
                .accountLocked(false)
                .enabled(
                        appProperties
                                .getSecurity()
                                .isCreateEnabledUsers()
                )
                .role(USER)
                .createdAt(LocalDateTime.now())
                .build();
    }

    private String generatePlainTextPassword() {
        return random.ints(
                        appProperties
                                .getSecurity()
                                .getPasswordGenerator()
                                .passwordLimits()
                                .left(),
                        appProperties
                                .getSecurity()
                                .getPasswordGenerator()
                                .passwordLimits()
                                .right() + 1)
                .filter(i ->
                        (i <= 57 || i >= 65) && (i <= 90 || i >= 97)
                )
                .limit(
                        appProperties
                                .getSecurity()
                                .getPasswordGenerator()
                                .passwordLength())
                .collect(
                        StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append
                )
                .toString();
    }
}