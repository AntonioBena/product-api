package com.tis.interview.product.configuration.client;

import com.tis.interview.product.dto.HnbResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class GetClient {
    private final RestClient restClient;

    public GetClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("")
                .build();
    }

    public List<HnbResponse> getHnbExchangeRate() {
        return restClient.get()
                .uri("/hnb-api")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }
}
