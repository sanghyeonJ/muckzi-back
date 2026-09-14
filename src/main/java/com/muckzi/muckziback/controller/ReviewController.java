package com.muckzi.muckziback.controller;

import com.muckzi.muckziback.dto.ReviewRequest;
import com.muckzi.muckziback.dto.ReviewResponse;
import com.muckzi.muckziback.entity.Review;
import com.muckzi.muckziback.repository.ReviewRepository;
import com.muckzi.muckziback.service.ReviewService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/places/{placeId}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final ReviewService reviewService;

    @GetMapping
    public List<ReviewResponse> getReviews(@PathVariable Long placeId) {
        return reviewRepository
                .findByPlace_PlaceIdAndStatus(placeId, Review.Status.ACTIVE)
                .stream()
                .map(ReviewResponse::new)
                .toList();
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public void createReview(
            @PathVariable Long placeId,
            @Valid @RequestBody ReviewRequest request,
            Authentication authentication
    ) {
        String userId = authentication.getName();

        reviewService.createReview(placeId, userId, request);
    }


    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{reviewId}")
    public void updateReview(
            @PathVariable Long placeId,
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewRequest request,
            Authentication authentication
    ) {
        String userId = authentication.getName();

        reviewService.updateReview(reviewId, userId, request);
    }

}
