package com.tis.interview.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tis.interview.product.BaseTest;
import com.tis.interview.product.exception.ExceptionResponse;
import com.tis.interview.product.model.dto.response.AuthResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.tis.interview.product.utils.TestUtils.generateAuthRequest;
import static com.tis.interview.product.utils.TestUtils.generateUserRegistrationRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthenticationControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testShouldCreateNewUserAndReturnPassword() throws Exception {
        var request = generateUserRegistrationRequest("test@test1.com", "name", "lastName");

        var result = mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andReturn().getResponse().getContentAsString();

        var resultToResp = mapper.readValue(result, AuthResponse.class);

        assertTrue(resultToResp.isSuccess());
        assertEquals(8, resultToResp.getPassword().length());
    }

    @Test
    void testShouldAuthenticateUserWithAccount() throws Exception {
        var request = generateAuthRequest("test@test.com", getTestPassword());

        var result = mockMvc.perform(post("/account/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();

        var resultToResp = mapper.readValue(result, AuthResponse.class);
        assertEquals("User authenticated successfully", resultToResp.getDescription());
    }

    @Test
    void testShouldNotAuthenticateUserWithoutAccount() throws Exception {
        var request = generateAuthRequest("test@test.com", "pass1234569");

        var result = mockMvc.perform(post("/account/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andReturn().getResponse().getContentAsString();

        var resultToResp = mapper.readValue(result, ExceptionResponse.class);
        assertEquals("Email and / or password is incorrect", resultToResp.getDescription());
    }

    @Test
    void testShouldNotCreateExistingUser() throws Exception {
        var request = generateUserRegistrationRequest("test@test.com", "name", "lastName");

        var result = mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError())
                .andReturn().getResponse().getContentAsString();

        var resultToResp = mapper.readValue(result, ExceptionResponse.class);

        assertEquals("Account already created", resultToResp.getDescription());
    }
}