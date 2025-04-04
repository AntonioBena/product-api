package com.tis.interview.product.service.review;

import com.tis.interview.product.dto.ProductDto;
import com.tis.interview.product.dto.ReviewDto;
import com.tis.interview.product.dto.response.PageResponse;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService{
    @Override
    public void createOrUpdateReview(ProductDto request) {

    }

    @Override
    public void deleteReview(String productCode) {

    }

    @Override
    public PageResponse<ReviewDto> getAllDisplayableReviews(Long productId, int page, int size) {
        return null;
    }
}
