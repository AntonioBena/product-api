package com.tis.interview.product.controller;


import com.tis.interview.product.model.dto.ReviewDto;
import com.tis.interview.product.model.dto.response.PageResponse;
import com.tis.interview.product.service.review.ReviewServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Review")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "review")
public class ReviewController {
    private final ReviewServiceImpl reviewService;

    @Operation(
            description = "Endpoint for creating and updating Reviews",
            summary = "Creates or updates Review, product must be created before review"
    )
    @PostMapping
    public ResponseEntity<HttpStatus> createOrUpdateReview(@RequestBody ReviewDto reviewDto,
                                                           @RequestParam(value = "productCode") String productCode) {
        reviewService.createOrUpdateReview(reviewDto, productCode);
        return ResponseEntity.accepted().build();
    }

    @Operation(
            description = "Endpoint for fetching Reviews by page and product code",
            summary = "Fetches reviews by page, product code (if not specified it will fetch ten by ten)"
    )
    @GetMapping
    public ResponseEntity<PageResponse<ReviewDto>> getAllReviewsByProductCode(
            @RequestParam(name = "productCode") String productCode,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        return ResponseEntity
                .ok(reviewService.getAllDisplayableReviews(productCode, page, size));
    }
}