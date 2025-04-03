package com.tis.interview.product.service;

import com.tis.interview.product.dto.ProductDto;

public interface ProductService {
    void createOrUpdateProduct(ProductDto request);
}