package com.muckzi.muckziback.repository;

import com.muckzi.muckziback.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    // 관리자 댓글 검색 (keyword, status가 null이면 해당 조건은 무시)
    @Query(
            value = "SELECT c FROM Comment c " +
                    "JOIN FETCH c.user u " +
                    "JOIN FETCH c.post p " +
                    "WHERE (c.content LIKE CONCAT('%', :keyword, '%') " +
                    "       OR u.userId LIKE CONCAT('%', :keyword, '%') " +
                    "       OR u.nickname LIKE CONCAT('%', :keyword, '%')) " +
                    "AND (:status IS NULL OR c.status = :status)",
            countQuery = "SELECT COUNT(c) FROM Comment c " +
                    "JOIN c.user u " +
                    "WHERE (c.content LIKE CONCAT('%', :keyword, '%') " +
                    "       OR u.userId LIKE CONCAT('%', :keyword, '%') " +
                    "       OR u.nickname LIKE CONCAT('%', :keyword, '%')) " +
                    "AND (:status IS NULL OR c.status = :status)"
    )
    Page<Comment> searchComments(
            @Param("keyword") String keyword,
            @Param("status") Comment.Status status,
            Pageable pageable
    );

    // 대시보드: 특정 상태 댓글 수
    long countByStatus(Comment.Status status);

}
