package com.tis.interview.product.service.review;

import com.tis.interview.product.exception.domain.ProductNotFoundException;
import com.tis.interview.product.exception.domain.ReviewNotFoundException;
import com.tis.interview.product.model.Review;
import com.tis.interview.product.model.dto.ReviewDto;
import com.tis.interview.product.model.dto.response.PageResponse;
import com.tis.interview.product.repository.ProductRepository;
import com.tis.interview.product.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.tis.interview.product.transformer.PageResponseTransformer.transformToPageResponse;

@Log4j2
@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final ModelMapper mapper;

    @Override
    public void createOrUpdateReview(ReviewDto request, String productCode) {
        var entity = mapper.map(request, Review.class);

        reviewRepository.findById(request.getId())
                .ifPresentOrElse(existingReview -> updateReview(entity, existingReview),
                        () -> createReview(entity, productCode)
                );
    }

    private void createReview(Review review, String productCode){
        var foundProduct = productRepository.findByCode(productCode)
                .orElseThrow(()-> new ProductNotFoundException("Product not found!"));

        review.setCreatedAt(LocalDateTime.now());
        review.setProduct(foundProduct);

        var createdReview = reviewRepository.save(review);
        log.info("Created Review: {}", createdReview);
    }

    @Transactional
    private void updateReview(Review request, Review existingReview){
        updateReviewDetails(request, existingReview);

        var updated = reviewRepository.save(existingReview);
        log.info("Updated Product: {}", updated);
    }

    private void updateReviewDetails(Review request, Review existingReview){
        existingReview.setText(request.getText());
        existingReview.setRating(request.getRating());
    }

    @Override
    public void deleteReview(Long id) {
        var fountReview = findReviewByIdOrThrow(id);
        reviewRepository.delete(fountReview);
    }

    @Override
    public PageResponse<ReviewDto> getAllDisplayableReviews(String productCode, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return transformToPageResponse(reviewRepository.findAllByProductCode(productCode, pageable), ReviewDto.class);
    }

    private Review findReviewByIdOrThrow(Long id){
        return reviewRepository.findById(id)
                .orElseThrow(()-> new ReviewNotFoundException("Review not found!"));
    }
}