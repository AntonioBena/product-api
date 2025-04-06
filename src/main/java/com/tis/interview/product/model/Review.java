package com.tis.interview.product.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "review")
public class Review extends BaseEntity {
    @NotEmpty(message = "Product review is mandatory")
    @Column(name = "description", columnDefinition = "text")
    private String text;
    @NotNull(message = "Product rating is mandatory")
    private int rating;
    private LocalDateTime createdAt;

    @Valid
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Valid
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "reviewer_id", nullable = false)
    private UserEntity reviewer;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return rating == review.rating && Objects.equals(text, review.text) && Objects.equals(createdAt, review.createdAt) && Objects.equals(product, review.product) && Objects.equals(reviewer, review.reviewer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, rating, createdAt, product, reviewer);
    }

    @Override
    public String toString() {
        return "Review{" +
                "text='" + text + '\'' +
                ", rating=" + rating +
                ", createdAt=" + createdAt +
                ", product=" + product +
                ", reviewer=" + reviewer +
                '}';
    }
}