package com.tis.interview.product.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "review")
public class Review extends BaseEntity {
    // - REVIEWER
    @ManyToOne
    @JoinColumn(name = "comment_post_id", nullable = false)
    private Product product;
    @NotEmpty(message = "Product review is mandatory")
    @Column(name = "description", columnDefinition = "text")
    private String text;
    @NotNull(message = "Product rating is mandatory")
    private int rating;
}