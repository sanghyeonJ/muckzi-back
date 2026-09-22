package com.muckzi.muckziback.repository;

import com.muckzi.muckziback.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r join fetch r.user where r.place.placeId = :placeId and r.status = :status")
    List<Review> findByPlace_PlaceIdAndStatus(@Param("placeId") Long placeId, @Param("status") Review.Status status);

    @Query("select r from Review r join fetch r.place where r.user.userId = :userId and r.status = :status order by r.createdAt desc")
    List<Review> findByUser_UserIdAndStatusOrderByCreatedAtDesc(@Param("userId") String userId, @Param("status") Review.Status status);

}
