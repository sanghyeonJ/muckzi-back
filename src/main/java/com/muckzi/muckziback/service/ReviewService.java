package com.muckzi.muckziback.service;

import com.muckzi.muckziback.dto.ReviewRequest;
import com.muckzi.muckziback.entity.Place;
import com.muckzi.muckziback.entity.Review;
import com.muckzi.muckziback.entity.User;
import com.muckzi.muckziback.repository.PlaceRepository;
import com.muckzi.muckziback.repository.ReviewRepository;
import com.muckzi.muckziback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final ReviewRepository reviewRepository;

    public void createReview(
            Long placeId,
            String userId,
            ReviewRequest request
    ) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다."));
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("음식점을 찾을 수 없습니다."));

        if(place.getStatus() != Place.Status.ACTIVE){
            throw new IllegalArgumentException("삭제된 음식점에는 리뷰를 작성할 수 없습니다.");
        }

        Review review = Review.builder()
                .user(user)
                .place(place)
                .content(request.getContent())
                .build();
        reviewRepository.save(review);
    }


    @Transactional
    public void updateReview(
            Long reviewId,
            String userId,
            ReviewRequest request
    ) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        if (review.getStatus() != Review.Status.ACTIVE) {
            throw new IllegalArgumentException(
                    "수정할 수 없는 리뷰입니다."
            );
        }

        if (!review.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException(
                    "본인이 작성한 리뷰만 수정할 수 있습니다."
            );
        }

        review.setContent(request.getContent());
        review.setUpdatedAt(java.time.LocalDateTime.now());
    }

}
