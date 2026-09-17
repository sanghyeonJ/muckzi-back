package com.muckzi.muckziback.repository;

import com.muckzi.muckziback.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Optional<Bookmark> findByUser_UserIdAndPlace_PlaceId(String userId, Long placeId);

}
