package com.tis.interview.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tis.interview.product.BaseTest;
import com.tis.interview.product.model.dto.ProductDto;
import com.tis.interview.product.model.dto.response.PageResponse;
import com.tis.interview.product.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static com.tis.interview.product.utils.TestUtils.prepareProductDto;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ProductControllerTest extends BaseTest {
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    void testShouldCreateNewProduct() throws Exception {
        var request =
                prepareProductDto("cbnfhtuisk106lb", "prod1", "desc", new BigDecimal("12.55"));

        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(httpBasic("test@test.com", getTestPassword()))
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isAccepted());

        assertEquals(productRepository.findAll()
                .stream()
                .findFirst()
                .get()
                .getCode(), request.getProductCode());
    }

    @Test
    void testShouldNotCreateNewProductWithMalformedCode() throws Exception {
        var request =
                prepareProductDto("106lb", "prod1", "desc", new BigDecimal("12.55"));

        var resp = mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(httpBasic("test@test.com", getTestPassword()))
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is5xxServerError()).andReturn().getResponse().getContentAsString();

        assertTrue(resp.contains("Product code must be exactly 15 characters long"));
    }

    @Test
    void testShouldGetAllProductsByFilterAndPagination() throws Exception {
        var p1 = prepareProductDto("abcdef123456789", "Product Alpha",
                "Description A", new BigDecimal("19.99"));
        var p2 = prepareProductDto("uvwxyz987654321", "Product Beta",
                "Description B", new BigDecimal("29.99"));

        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(httpBasic("test@test.com", getTestPassword()))
                        .content(mapper.writeValueAsString(p1)))
                .andExpect(status().isAccepted());

        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(httpBasic("test@test.com", getTestPassword()))
                        .content(mapper.writeValueAsString(p2)))
                .andExpect(status().isAccepted());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/product")
                        .with(httpBasic("test@test.com", getTestPassword()))
                        .param("productName", "Alpha")
                        .param("page", "0")
                        .param("size", "5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();

        PageResponse<?> pageResponse = mapper.readValue(jsonResponse, PageResponse.class);
        assertEquals(1, pageResponse.getContent().size());
    }

    @Test
    void testShouldGetProductByCodeOnly() throws Exception {
        var p1 = prepareProductDto("productcode0011", "Product One",
                "Desc1", new BigDecimal("15.00"));
        var p2 = prepareProductDto("productcode0022", "Product Two",
                "Desc2", new BigDecimal("25.00"));

        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(httpBasic("test@test.com", getTestPassword()))
                        .content(mapper.writeValueAsString(p1)))
                .andExpect(status().isAccepted());

        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(httpBasic("test@test.com", getTestPassword()))
                        .content(mapper.writeValueAsString(p2)))
                .andExpect(status().isAccepted());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/product")
                        .with(httpBasic("test@test.com", getTestPassword()))
                        .param("productCode", "productcode0011")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        PageResponse<?> page = mapper.readValue(json, PageResponse.class);

        assertEquals(1, page.getContent().size());
    }

    @Test
    void testShouldGetProductByCode() throws Exception {
        var request = prepareProductDto("abc123def456ghi", "Product XYZ",
                "Some product desc", new BigDecimal("49.99"));

        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(httpBasic("test@test.com", getTestPassword()))
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isAccepted());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/product/{productCode}", "abc123def456ghi")
                        .with(httpBasic("test@test.com", getTestPassword()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        ProductDto responseDto = mapper.readValue(jsonResponse, ProductDto.class);

        assertEquals("Product XYZ", responseDto.getProductName());
        assertEquals("abc123def456ghi", responseDto.getProductCode());
        assertEquals(new BigDecimal("49.99"), responseDto.getProductPrice());
        assertEquals(new BigDecimal("54.99"), responseDto.getProductPriceUsd());
    }
}