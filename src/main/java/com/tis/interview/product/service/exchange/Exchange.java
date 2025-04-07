package com.tis.interview.product.service.exchange;

import com.tis.interview.product.configuration.ApplicationProperties;
import com.tis.interview.product.configuration.client.GetClient;
import com.tis.interview.product.exception.domain.ExchangeFetchWxception;
import com.tis.interview.product.exception.domain.ExchangeNotFoundException;
import com.tis.interview.product.model.ExchangeCache;
import com.tis.interview.product.model.dto.response.HnbResponse;
import com.tis.interview.product.repository.ExchangeRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.Executors;

@Profile("prod")
@Log4j2
@Component
@AllArgsConstructor
public class Exchange implements SchedulingConfigurer {
    private final ApplicationProperties appProperties;
    private final GetClient getClient;
    private final ExchangeRepository exchangeRepository;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(Executors.newSingleThreadScheduledExecutor());

        CronExpression cron = CronExpression.parse(appProperties.getExchangeScheduler().getCron());
        ZoneId zoneId = ZoneId.of(appProperties.getExchangeScheduler().getTimezone());

        taskRegistrar.addTriggerTask(
                this::createOrUpdateExchange,
                triggerContext -> {
                    var lastExecution =
                            triggerContext.lastScheduledExecution() != null
                                    ? Objects.requireNonNull(
                                    triggerContext.lastScheduledExecution()
                            ).atZone(zoneId)
                                    : ZonedDateTime.now(zoneId);

                    var nextExecution = cron.next(lastExecution);

                    return Date
                            .from(nextExecution.toInstant())
                            .toInstant();
                }
        );
    }

    @PostConstruct
    public void runAtStartup() {
        createOrUpdateExchange();
    }

    private void createOrUpdateExchange() {
        log.debug("Pulling exchange from api");
        var clientResult = getClient.getHnbExchangeRate()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ExchangeFetchWxception("Problem with fetching data from hnb"));

        if (exchangeRepository.count() == 1) {
            updateExchange(clientResult);
            return;
        }
        createExchange(clientResult);
    }

    private void createExchange(HnbResponse response) {
        var newExchange = buildExchangeFromResponse(response);
        exchangeRepository.save(newExchange);
        log.debug("created exchange cache: {}", newExchange);
    }

    @Transactional
    private void updateExchange(HnbResponse response) {
        var found = exchangeRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ExchangeNotFoundException("Can not update non existing exchange!"));

        updateExchangeValues(found, response);

        exchangeRepository.save(found);
        log.debug("updated exchange cache: {}", found);
    }

    private void updateExchangeValues(ExchangeCache found, HnbResponse response) {
        found.setCurrency(response.getCurrency());
        found.setValidFrom(LocalDate.parse(response.getApplyDate()));
        found.setCurrency(response.getCurrency());
        found.setLastExchangeRateFetch(LocalDateTime.now());
        found.setCurrencyValue(
                bigDecimalParser(response.getMiddleRate())
        );
    }

    private ExchangeCache buildExchangeFromResponse(HnbResponse response) {
        return ExchangeCache.builder()
                .validFrom(LocalDate.parse(response.getApplyDate()))
                .currency(response.getCurrency())
                .lastExchangeRateFetch(LocalDateTime.now())
                .currencyValue(
                        bigDecimalParser(response.getMiddleRate())
                )
                .build();
    }

    private BigDecimal bigDecimalParser(String number) {
        return new BigDecimal(number
                .replace(",", "."));
    }
}