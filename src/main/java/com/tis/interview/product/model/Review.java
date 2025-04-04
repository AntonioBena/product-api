package com.tis.interview.product.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@EqualsAndHashCode //TODO remove
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "review")
public class Review extends BaseEntity {
    // - REVIEWER TODO this will be from spring security context
    @NotEmpty(message = "Product review is mandatory")
    @Column(name = "description", columnDefinition = "text")
    private String text;
    @NotNull(message = "Product rating is mandatory")
    private int rating;

    @Valid
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}