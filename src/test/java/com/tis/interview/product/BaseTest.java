package com.tis.interview.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tis.interview.product.model.ExchangeCache;
import com.tis.interview.product.model.dto.response.AuthResponse;
import com.tis.interview.product.repository.ExchangeRepository;
import com.tis.interview.product.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.tis.interview.product.utils.TestUtils.generateUserRegistrationRequest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BaseTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    private String testPassword = "";

    @Autowired
    private ExchangeRepository exchangeRepository;

    public String getTestPassword() {
        return testPassword;
    }

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() throws Exception {
        var request = generateUserRegistrationRequest("test@test.com", "name", "lastName");
        var result = mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andReturn().getResponse().getContentAsString();

        var resultToResp = mapper.readValue(result, AuthResponse.class);
        testPassword = resultToResp.getPassword();

        var cache = ExchangeCache.builder()
                .lastExchangeRateFetch(LocalDateTime.now())
                .currencyValue(new BigDecimal("1.10"))
                .validFrom(LocalDate.now())
                .build();
        exchangeRepository.save(cache);
    }

    @AfterEach
    void tearDown() {
        exchangeRepository.deleteAll();
        userRepository.deleteAll();
    }
}