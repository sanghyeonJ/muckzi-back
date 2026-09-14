package com.muckzi.muckziback.repository;

import com.muckzi.muckziback.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByPlace_PlaceIdAndStatus(Long placeId, Review.Status status);

}
