package com.tis.interview.product.model.dto;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    Long id;
    private String text;
    private int rating;
    private ProductDto product;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ReviewDto reviewDto = (ReviewDto) o;
        return rating == reviewDto.rating && Objects.equals(id, reviewDto.id) && Objects.equals(text, reviewDto.text) && Objects.equals(product, reviewDto.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, rating, product);
    }

    @Override
    public String toString() {
        return "ReviewDto{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", rating=" + rating +
                ", product=" + product +
                '}';
    }
}