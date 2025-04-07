package com.tis.interview.product.service.exchange;

import com.tis.interview.product.exception.domain.ExchangeNotFoundException;
import com.tis.interview.product.model.ExchangeCache;
import com.tis.interview.product.repository.ExchangeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@AllArgsConstructor
public class PriceCalculator {
    private final ExchangeRepository exchangeRepository;

    public BigDecimal calculatePriceUSD(BigDecimal valueToCalc, int scale, RoundingMode roundingMode) {
        var exchange = getExchange();

        return valueToCalc.multiply(exchange.getCurrencyValue())
                .setScale(scale, roundingMode);
    }

    private ExchangeCache getExchange() {
        return exchangeRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ExchangeNotFoundException("Exchange not present!"));
    }
}