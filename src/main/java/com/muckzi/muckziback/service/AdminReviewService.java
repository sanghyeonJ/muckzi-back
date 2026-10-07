package com.muckzi.muckziback.service;

import com.muckzi.muckziback.dto.AdminReviewResponse;
import com.muckzi.muckziback.entity.Review;
import com.muckzi.muckziback.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminReviewService {

    private final ReviewRepository reviewRepository;

    // 리뷰 목록 조회
    @Transactional(readOnly = true)
    public Page<AdminReviewResponse> getReviews (
            String keyword,
            Review.Status status,
            int page,
            int size
    ) {
        keyword = (keyword == null) ? "" : keyword.trim();

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        return reviewRepository.searchReviews(keyword, status, pageable)
                .map(AdminReviewResponse::new);
    }

    // 리뷰 상태 변경
    @Transactional
    public void updateReviewStatus (long reviewId, Review.Status status) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
        if (review.getStatus() == status) {
            throw new IllegalArgumentException(
                    status == Review.Status.DELETED ? "이미 삭제된 리뷰입니다." : "이미 정상 상태인 리뷰입니다."
            );
        }
        review.setStatus(status);
    }

}
