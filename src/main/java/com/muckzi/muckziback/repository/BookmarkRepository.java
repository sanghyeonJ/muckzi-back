package com.muckzi.muckziback.repository;

import com.muckzi.muckziback.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Optional<Bookmark> findByUser_UserIdAndPlace_PlaceId(String userId, Long placeId);

    @Query("select b from Bookmark b join fetch b.place where b.user.userId = :userId order by b.createdAt desc")
    List<Bookmark> findByUser_UserIdOrderByCreatedAtDesc(@Param("userId") String userId);

}
