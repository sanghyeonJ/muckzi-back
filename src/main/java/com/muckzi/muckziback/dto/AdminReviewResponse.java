package com.muckzi.muckziback.dto;

import com.muckzi.muckziback.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminReviewResponse {

    private final Long reviewId;
    private final Long placeId;
    private final String placeName;
    private final String userId;
    private final String nickname;
    private final String content;
    private final Review.Status status;
    private final LocalDateTime createdAt;

    public AdminReviewResponse (Review review) {
        this.reviewId = review.getReviewId();
        this.placeId = review.getPlace().getPlaceId();
        this.placeName = review.getPlace().getPlaceName();
        this.userId = review.getUser().getUserId();
        this.nickname = review.getUser().getNickname();
        this.content = review.getContent();
        this.status = review.getStatus();
        this.createdAt = review.getCreatedAt();
    }

}
