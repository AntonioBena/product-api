package com.tis.interview.product.controller;


import com.tis.interview.product.service.review.ReviewServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Review")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "review")
public class ReviewController {
    private final ReviewServiceImpl reviewService;
}
