package com.tis.interview.product.model.dto;

import com.tis.interview.product.model.Product;
import lombok.*;

@EqualsAndHashCode
@ToString //TODO remove
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    Long id;
    private String text;
    private int rating;
    private Product product;
}