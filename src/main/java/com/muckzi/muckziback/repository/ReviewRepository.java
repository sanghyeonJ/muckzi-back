package com.muckzi.muckziback.repository;

import com.muckzi.muckziback.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r join fetch r.user where r.place.placeId = :placeId and r.status = :status")
    List<Review> findByPlace_PlaceIdAndStatus(@Param("placeId") Long placeId, @Param("status") Review.Status status);

    @Query("select r from Review r join fetch r.place where r.user.userId = :userId and r.status = :status order by r.createdAt desc")
    List<Review> findByUser_UserIdAndStatusOrderByCreatedAtDesc(@Param("userId") String userId, @Param("status") Review.Status status);

    // 관리자
    @Query(
            value = "select r from Review r " +
                    "join fetch r.user u " +
                    "join fetch r.place p " +
                    "where (:keyword is null " +
                    "       or r.content like concat('%', :keyword, '%') " +
                    "       or p.placeName like concat('%', :keyword, '%') " +
                    "       or u.userId like concat('%', :keyword, '%') " +
                    "       or u.nickname like concat('%', :keyword, '%')) " +
                    "and (:status is null or r.status = :status)",
            countQuery = "select count(r) from Review r " +
                    "join r.user u " +
                    "join r.place p " +
                    "where (:keyword is null " +
                    "       or r.content like concat('%', :keyword, '%') " +
                    "       or p.placeName like concat('%', :keyword, '%') " +
                    "       or u.userId like concat('%', :keyword, '%') " +
                    "       or u.nickname like concat('%', :keyword, '%')) " +
                    "and (:status is null or r.status = :status)"
    )
    Page<Review> searchReviews (
            @Param("keyword") String keyword,
            @Param("status") Review.Status status,
            Pageable pageable
    );

    // 대시보드: 특정 상태 리뷰 수
    long countByStatus(Review.Status status);
    // 대시보드: 특정 상태 + 특정 시각 이후 작성된 리뷰 수 (오늘 리뷰)
    long countByStatusAndCreatedAtGreaterThanEqual(Review.Status status, LocalDateTime start);

}
