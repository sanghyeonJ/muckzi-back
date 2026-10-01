package com.muckzi.muckziback.controller;

import com.muckzi.muckziback.dto.AdminReviewResponse;
import com.muckzi.muckziback.entity.Review;
import com.muckzi.muckziback.service.AdminReviewService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/reviews")
@RequiredArgsConstructor
public class AdminReviewController {

    private final AdminReviewService adminReviewService;

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public Page<AdminReviewResponse> getReviews (
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Review.Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return adminReviewService.getReviews(keyword, status, page, size);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/{reviewId}/status")
    public void updateReviewStatus (
            @PathVariable Long reviewId,
            @RequestParam Review.Status status
    ) {
        adminReviewService.updateReviewStatus(reviewId, status);
    }

}
