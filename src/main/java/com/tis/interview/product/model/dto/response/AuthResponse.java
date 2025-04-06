package com.tis.interview.product.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AuthResponse<T> {
    private boolean success;
    private String message;
    private String description;
    private String error;
    private String password;
    private T data;
}