package com.muckzi.muckziback.controller;

import com.muckzi.muckziback.dto.MyReviewResponse;
import com.muckzi.muckziback.dto.ReviewRequest;
import com.muckzi.muckziback.dto.ReviewResponse;
import com.muckzi.muckziback.entity.Place;
import com.muckzi.muckziback.entity.Review;
import com.muckzi.muckziback.repository.ReviewRepository;
import com.muckzi.muckziback.service.ReviewService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final ReviewService reviewService;

    @GetMapping("/{placeId}/reviews")
    public List<ReviewResponse> getReviews(@PathVariable Long placeId) {
        return reviewRepository
                .findByPlace_PlaceIdAndStatus(placeId, Review.Status.ACTIVE)
                .stream()
                .map(ReviewResponse::new)
                .toList();
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/reviews/me")
    public List<MyReviewResponse> getMyReviews(Authentication authentication) {
        String userId = authentication.getName();

        return reviewRepository
                .findByUser_UserIdAndStatusOrderByCreatedAtDesc(userId, Review.Status.ACTIVE)
                .stream()
                .map(MyReviewResponse::new)
                .toList();
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/reviews")
    public Map<String, Long> createReview(
            @Valid @RequestBody ReviewRequest request,
            Authentication authentication
    ) {
        String userId = authentication.getName();

        Place place = reviewService.createReview(userId, request);

        return Map.of("placeId", place.getPlaceId());
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/reviews/{reviewId}")
    public void updateReview(
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewRequest request,
            Authentication authentication
    ) {
        String userId = authentication.getName();

        reviewService.updateReview(reviewId, userId, request);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/reviews/{reviewId}")
    public void deleteReview(
            @PathVariable Long reviewId,
            Authentication authentication
    ) {
        String userId = authentication.getName();

        reviewService.deleteReview(reviewId, userId);
    }
}