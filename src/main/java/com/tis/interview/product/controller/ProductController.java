package com.tis.interview.product.controller;

import com.tis.interview.product.model.dto.ProductDto;
import com.tis.interview.product.model.dto.response.PageResponse;
import com.tis.interview.product.model.dto.response.PopularProductsResponse;
import com.tis.interview.product.service.product.ProductServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Product")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "product")
public class ProductController {
    private final ProductServiceImpl productService;

    @Operation(
            description = "Endpoint for creating and updating Products",
            summary = "Creates or updates Product"
    )
    @PostMapping
    public ResponseEntity<HttpStatus> createOrUpdateProduct(@RequestBody ProductDto productDto) {
        productService.createOrUpdateProduct(productDto);
        return ResponseEntity.accepted().build();
    }

    @Operation(
            description = "Endpoint for fetching Popular Products by page",
            summary = "Fetches popular products by page with average rating"
    )
    @GetMapping(value = "/popular")
    public ResponseEntity<PageResponse<PopularProductsResponse>> getPopularProductsByPage(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "3", required = false) int size) {
        return ResponseEntity
                .ok(productService.getPopularProducts(page, size));
    }

    @Operation(
            description = "Endpoint for fetching Products by page with optional filters",
            summary = "Fetches products by page with optional filters (if not specified it will fetch ten by ten)"
    )
    @GetMapping
    public ResponseEntity<PageResponse<ProductDto>> getAllProductsByPage(
            @RequestParam(name = "productName", required = false) String productName,
            @RequestParam(name = "productCode", required = false) String productCode,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        return ResponseEntity.ok(productService.getAllDisplayableProducts(page, size, productName, productCode));
    }

    @Operation(
            description = "Endpoint for fetching Product by code",
            summary = "Fetches product by product code"
    )
    @GetMapping(value = "/{productCode}")
    public ResponseEntity<ProductDto> getProductByCode(@PathVariable(value = "productCode") String productCode) {
        return ResponseEntity.ok(productService.findProductByCode(productCode));
    }

    @Operation(
            description = "Endpoint for deleting Product by code",
            summary = "Deletes product by product code"
    )
    @DeleteMapping("/delete/{productCode}")
    public ResponseEntity<HttpStatus> deleteProductById(@PathVariable(value = "productCode") String productCode) {
        productService.deleteProduct(productCode);
        return ResponseEntity.ok().build();
    }
}