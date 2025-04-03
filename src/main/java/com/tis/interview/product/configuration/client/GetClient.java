package com.tis.interview.product.configuration.client;

import com.tis.interview.product.configuration.ApplicationProperties;
import com.tis.interview.product.dto.HnbResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class GetClient {
    private final RestClient restClient;
    private final ApplicationProperties appProperties;

    public GetClient(RestClient.Builder builder, ApplicationProperties appProperties) {
        this.appProperties = appProperties;
        this.restClient = builder
                .baseUrl(
                        appProperties.getHnb().getApiUrl()
                )
                .build();
    }

    public List<HnbResponse> getHnbExchangeRate() {
        return restClient.get()
                .uri(
                        appProperties.getHnb().getCurrencyUri() +
                                appProperties.getHnb().getCurrency()
                )
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }
}
