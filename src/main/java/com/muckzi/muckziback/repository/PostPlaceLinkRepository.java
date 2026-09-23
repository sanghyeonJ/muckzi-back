package com.muckzi.muckziback.repository;

import com.muckzi.muckziback.entity.PostPlaceLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostPlaceLinkRepository extends JpaRepository<PostPlaceLink, Long> {

    @Query("select l from PostPlaceLink l join fetch l.place where l.post.postId = :postId")
    List<PostPlaceLink> findByPost_PostId(@Param("postId") Long postId);

    void deleteByPost_PostId(Long postId);

}
