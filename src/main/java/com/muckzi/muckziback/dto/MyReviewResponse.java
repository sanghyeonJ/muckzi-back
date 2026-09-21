package com.muckzi.muckziback.dto;

import com.muckzi.muckziback.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyReviewResponse {

    private final Long reviewId;
    private final Long placeId;
    private final String placeName;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public MyReviewResponse(Review review) {
        this.reviewId = review.getReviewId();
        this.placeId = review.getPlace().getPlaceId();
        this.placeName = review.getPlace().getPlaceName();
        this.content = review.getContent();
        this.createdAt = review.getCreatedAt();
        this.updatedAt = review.getUpdatedAt();
    }

}
