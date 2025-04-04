package com.tis.interview.product.service.review;

import com.tis.interview.product.model.dto.ReviewDto;
import com.tis.interview.product.model.dto.response.PageResponse;

public interface ReviewService {
    void createOrUpdateReview(ReviewDto request, String productCode);
    void deleteReview(Long id);
    PageResponse<ReviewDto> getAllDisplayableReviews(Long productId, int page, int size);
}