package com.tis.interview.product.configuration.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tis.interview.product.configuration.ApplicationProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.io.IOException;

import static com.tis.interview.product.utils.TestUtils.readJsonFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


@RestClientTest(GetClient.class)
class GetClientTest {
    @Autowired
    private MockRestServiceServer server;
    @Autowired
    private GetClient getClient;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ApplicationProperties appProps;

    @Test
    void getHnbExchangeRate() throws IOException {
        String validExchange = readJsonFile("src/test/resources/jsons/valid_usd_hnb_excange.json");

        this.server.expect(
                        requestTo("https://api.test.hr/tecajn-eur/v3?valuta=USD")
                )
                .andRespond(
                        withSuccess(validExchange, MediaType.APPLICATION_JSON)
                );

        var hnbResponse = getClient.getHnbExchangeRate();

        assertEquals(1, hnbResponse.size());
        assertEquals("2025-04-03", hnbResponse.getFirst().getApplyDate());
        assertEquals("1,080300", hnbResponse.getFirst().getMiddleRate());
        assertEquals("USD", hnbResponse.getFirst().getCurrency());
    }
}