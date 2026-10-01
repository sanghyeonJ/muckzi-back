package com.muckzi.muckziback.repository;

import com.muckzi.muckziback.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(
            value = "select p from Post p join fetch p.user where p.status = :status order by p.createdAt desc",
            countQuery = "select count(p) from Post p where p.status = :status"
    )
    Page<Post> findByStatusOrderByCreatedAtDesc(@Param("status") Post.Status status, Pageable pageable);

    // 관리자가 게시글 검색
    @Query(
            value = "select p from Post p join fetch p.user u " +
                    "where (:keyword is null " +
                    "       or p.title like concat('%', :keyword, '%') " +
                    "       or u.userId like concat('%', :keyword, '%') " +
                    "       or u.nickname like concat('%', :keyword, '%')) " +
                    "and (:status is null or p.status = :status)",
            countQuery = "select count(p) from Post p join p.user u " +
                    "where (:keyword is null " +
                    "       or p.title like concat('%', :keyword, '%') " +
                    "       or u.userId like concat('%', :keyword, '%') " +
                    "       or u.nickname like concat('%', :keyword, '%')) " +
                    "and (:status is null or p.status = :status)"
    )
    Page<Post> searchPost(
            @Param("keyword") String keyword,
            @Param("status") Post.Status status,
            Pageable pageable
    );

    // 대시보드: 특정 상태 게시글 수
    long countByStatus(Post.Status status);
    // 대시보드: 특정 상태 + 특정 시각 이후 작성된 게시글 수 (오늘 게시글)
    long countByStatusAndCreatedAtGreaterThanEqual(Post.Status status, LocalDateTime start);

}
