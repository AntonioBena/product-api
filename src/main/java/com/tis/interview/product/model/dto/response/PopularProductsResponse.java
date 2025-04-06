package com.tis.interview.product.model.dto.response;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PopularProductsResponse {
    private String name;
    private float averageRating;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PopularProductsResponse that = (PopularProductsResponse) o;
        return Float.compare(averageRating, that.averageRating) == 0 && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, averageRating);
    }

    @Override
    public String toString() {
        return "PopularProductsResponse{" +
                "name='" + name + '\'' +
                ", averageRating=" + averageRating +
                '}';
    }
}