package com.tis.interview.product.service.review;

import com.tis.interview.product.dto.ProductDto;
import com.tis.interview.product.dto.ReviewDto;
import com.tis.interview.product.dto.response.PageResponse;

public interface ReviewService {
    void createOrUpdateReview(ProductDto request);
    void deleteReview(String productCode);
    PageResponse<ReviewDto> getAllDisplayableReviews(Long productId, int page, int size);
}