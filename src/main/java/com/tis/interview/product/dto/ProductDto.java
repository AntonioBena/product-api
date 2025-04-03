package com.tis.interview.product.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@Builder
public class ProductDto {
    private Long id;
    @Min(value = 15, message = "Code must be exactly 15 chars long")
    @Max(value = 15, message = "Code must be exactly 15 chars long")
    @NotEmpty(message = "Product code is mandatory")
    private String productCode;
    @NotEmpty(message = "Product name is mandatory")
    private String productName;
    @NotNull(message = "Product price is mandatory")
    private BigInteger price;
    @Column(name = "description", columnDefinition = "text")
    private String productDescription;
}