package com.muckzi.muckziback.repository;

import com.muckzi.muckziback.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(
            value = "select p from Post p join fetch p.user where p.status = :status order by p.createdAt desc",
            countQuery = "select count(p) from Post p where p.status = :status"
    )
    Page<Post> findByStatusOrderByCreatedAtDesc(@Param("status") Post.Status status, Pageable pageable);

}
