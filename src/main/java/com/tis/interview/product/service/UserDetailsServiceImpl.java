package com.tis.interview.product.service;

import com.tis.interview.product.exception.security.UnauthorizedException;
import com.tis.interview.product.exception.security.UserNotFoundException;
import com.tis.interview.product.model.UserEntity;
import com.tis.interview.product.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User " + userEmail + " not found"));
    }

    public UserEntity getAuthenticatedUserEntity() {
        var authUser = getCachedAuthenticatedUser();
        validateUserAccount(authUser);
        return authUser;
    }

    private void validateUserAccount(UserEntity user) {
        if (user.isAccountLocked() || !user.isEnabled() ||
                !user.isAccountNonExpired() || !user.isCredentialsNonExpired()) {
            throw new UnauthorizedException("Cannot return invalid user!");
        }
    }

    private UserEntity getCachedAuthenticatedUser() {
        return userRepository.findByEmail(getAuthenticatedUsername())
                .orElseThrow(() -> new UserNotFoundException("User does not exist!"));
    }

    private String getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new UnauthorizedException("User is not authenticated!");
        }
        return authentication.getName();
    }

    public void verifyUserPermission(UserEntity targetUser, String errorMessage) {
        if (!Objects.equals(targetUser.getId(), getCachedAuthenticatedUser().getId())) {
            throw new UnauthorizedException(errorMessage);
        }
    }
}