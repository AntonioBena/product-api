package com.tis.interview.product.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "exchange")
public class ExchangeCache extends BaseEntity {
    private LocalDateTime lastExchangeRateFetch;
    private LocalDateTime validFrom;
    private String currency;
    private BigInteger currencyValue;
}