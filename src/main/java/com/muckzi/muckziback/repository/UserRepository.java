package com.muckzi.muckziback.repository;

import com.muckzi.muckziback.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByNickname(String nickname);

    Optional<User> findByUserId(String userId);

    @Query("SELECT u FROM User u " +
            "WHERE (:keyword IS NULL " +
            "       OR u.userId LIKE CONCAT('%', :keyword, '%') " +
            "       OR u.nickname LIKE CONCAT('%', :keyword, '%')) " +
            "AND (:status IS NULL OR u.status = :status)")
    Page<User> searchUsers (
            @Param("keyword") String keyword,
            @Param("status") User.Status status,
            Pageable pageable
    );

}
