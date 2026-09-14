package com.muckzi.muckziback.dto;

import com.muckzi.muckziback.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResponse {

    private final Long reviewId;
    private final String userId;
    private final String nickname;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ReviewResponse(Review review){
        this.reviewId = review.getReviewId();
        this.userId = review.getUser().getUserId();
        this.nickname = review.getUser().getNickname();
        this.content = review.getContent();
        this.createdAt = review.getCreatedAt();
        this.updatedAt = review.getUpdatedAt();
    }

}
