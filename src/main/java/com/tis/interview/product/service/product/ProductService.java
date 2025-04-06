package com.tis.interview.product.service.product;

import com.tis.interview.product.model.dto.ProductDto;
import com.tis.interview.product.model.dto.response.PageResponse;
import com.tis.interview.product.model.dto.response.PopularProductsResponse;

public interface ProductService {
    void createOrUpdateProduct(ProductDto request);
    void deleteProduct(String productCode);
    PageResponse<ProductDto> getAllDisplayableProducts(int page, int size, String productName, String productCode);
    PageResponse<PopularProductsResponse> getPopularProducts(int page, int size);
}