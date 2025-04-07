package com.tis.interview.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tis.interview.product.model.dto.response.AuthResponse;
import com.tis.interview.product.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }
}