package com.tis.interview.product.dto;

import com.tis.interview.product.model.Review;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
//    @Min(value = 15, message = "Code must be exactly 15 chars long")
//    @Max(value = 15, message = "Code must be exactly 15 chars long")
    @NotEmpty(message = "Product code is mandatory")
    private String productCode;
    @NotEmpty(message = "Product name is mandatory")
    private String productName;
    @NotNull(message = "Product price is mandatory")
    private BigInteger productPrice;
    @Column(name = "description", columnDefinition = "text")
    private String productDescription;
}