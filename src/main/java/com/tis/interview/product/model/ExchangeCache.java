package com.tis.interview.product.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "exchange")
public class ExchangeCache extends BaseEntity {
    private LocalDateTime lastExchangeRateFetch;
    private LocalDate validFrom;
    private String currency;
    private BigDecimal currencyValue;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeCache that = (ExchangeCache) o;
        return Objects.equals(lastExchangeRateFetch, that.lastExchangeRateFetch) && Objects.equals(validFrom, that.validFrom) && Objects.equals(currency, that.currency) && Objects.equals(currencyValue, that.currencyValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastExchangeRateFetch, validFrom, currency, currencyValue);
    }

    @Override
    public String toString() {
        return "ExchangeCache{" +
                "lastExchangeRateFetch=" + lastExchangeRateFetch +
                ", validFrom=" + validFrom +
                ", currency='" + currency + '\'' +
                ", currencyValue=" + currencyValue +
                '}';
    }
}