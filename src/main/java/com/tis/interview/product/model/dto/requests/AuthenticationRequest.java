package com.tis.interview.product.model.dto.requests;

import com.tis.interview.product.validation.EmailValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;


import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {
    @EmailValidator(message = "Email is not formatted")
    @NotEmpty(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    private String accountId;
    @NotEmpty(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    private String password;

    @Override
    public String toString() {
        return "AuthenticationRequest{" +
                "email='" + accountId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticationRequest that = (AuthenticationRequest) o;
        return Objects.equals(accountId, that.accountId) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, password);
    }
}