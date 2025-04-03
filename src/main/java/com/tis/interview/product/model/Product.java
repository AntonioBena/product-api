package com.tis.interview.product.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product extends BaseEntity {
    @Min(value = 15, message = "Code must be exactly 15 chars long")
    @Max(value = 15, message = "Code must be exactly 15 chars long")
    private String code;
    @NotEmpty(message = "Product name is mandatory")
    private String name;
    @NotNull(message = "Product price is mandatory")
    private BigInteger priceEur;
    @Column(name = "description", columnDefinition = "text")
    private String description;
}