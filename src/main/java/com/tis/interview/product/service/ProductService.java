package com.tis.interview.product.service;

import com.tis.interview.product.dto.ProductDto;
import com.tis.interview.product.dto.response.PageResponse;

public interface ProductService {
    void createOrUpdateProduct(ProductDto request);
    void deleteProduct(String productCode);
    PageResponse<ProductDto> getAllDisplayableProducts(int page, int size);
}