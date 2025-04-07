package com.tis.interview.product.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
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
    private BigDecimal productPrice;
    private BigDecimal productPriceUsd;
    @Column(name = "description", columnDefinition = "text")
    private String productDescription;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return Objects.equals(id, that.id) && Objects.equals(productCode, that.productCode) && Objects.equals(productName, that.productName) && Objects.equals(productPrice, that.productPrice) && Objects.equals(productPriceUsd, that.productPriceUsd) && Objects.equals(productDescription, that.productDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productCode, productName, productPrice, productPriceUsd, productDescription);
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productPriceUsd=" + productPriceUsd +
                ", productDescription='" + productDescription + '\'' +
                '}';
    }
}