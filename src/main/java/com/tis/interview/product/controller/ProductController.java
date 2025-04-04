package com.tis.interview.product.controller;

import com.tis.interview.product.dto.ProductDto;
import com.tis.interview.product.dto.response.PageResponse;
import com.tis.interview.product.service.ProductServiceImpl;
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
            description = "Endpoint for fetching Products by page",
            summary = "Fetches products by page (if not specified it will fetch ten by ten)"
    )
    @GetMapping
    public ResponseEntity<PageResponse<ProductDto>> getAllProductsByPage(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        return ResponseEntity
                .ok(productService.getAllDisplayableProducts(page, size));
    }

    @Operation(
            description = "Endpoint for fetching Product by code",
            summary = "Fetches product by product code"
    )
    @GetMapping(value = "/{productCode}")
    public ResponseEntity<ProductDto> getProductByCode(@PathVariable(value = "productCode") String productCode){
        return ResponseEntity.ok(productService.findProductByCode(productCode));
    }

    @Operation(
            description = "Endpoint for deleting Product by code",
            summary = "Deletes product by product code"
    )
    @DeleteMapping("/delete/{productCode}")
    public ResponseEntity<HttpStatus> deleteBlogPostById(@PathVariable(value = "productCode") String productCode) {
        productService.deleteProduct(productCode);
        return ResponseEntity.ok().build();
    }
}