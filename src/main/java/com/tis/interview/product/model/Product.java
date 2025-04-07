package com.tis.interview.product.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor    //    @Min(value = 15, message = "Code must be exactly 15 chars long")
//    @Max(value = 15, message = "Code must be exactly 15 chars long")
@Entity
@Table(name = "product")
public class Product extends BaseEntity {
    private String code;
    @NotEmpty(message = "Product name is mandatory")
    private String name;
    @Column(name = "PRICE_EUR")
    @NotNull(message = "Product price is mandatory")
    private BigDecimal price;
    @Column(name = "description", columnDefinition = "text")
    private String description;
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(code, product.code) && Objects.equals(name, product.name) && Objects.equals(price, product.price) && Objects.equals(description, product.description) && Objects.equals(createdAt, product.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, price, description, createdAt);
    }

    @Override
    public String toString() {
        return "Product{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}