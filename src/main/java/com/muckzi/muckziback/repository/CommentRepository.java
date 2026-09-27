package com.muckzi.muckziback.repository;

import com.muckzi.muckziback.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 댓글 답글 전체
    @Query("SELECT c FROM Comment c " +
            "JOIN FETCH c.user " +
            "WHERE c.post.postId = :postId " +
            "ORDER BY c.createdAt ASC")
    List<Comment> findAllByPostIdWithUser(@Param("postId") Long postId);

    // 해당 댓글에 삭제되지않은 답글 확인
    boolean existsByParent_CommentIdAndStatus(Long parentId, Comment.Status status);

}
