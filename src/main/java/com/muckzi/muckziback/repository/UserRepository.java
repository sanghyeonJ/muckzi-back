package com.muckzi.muckziback.repository;

import com.muckzi.muckziback.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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

    // 대시보드: 상태가 특정 값이 아닌 회원 수 (탈퇴 제외할 때 사용)
    long countByStatusNot (User.Status status);
    // 대시보드: 특정 상태 회원 수 (차단 회원 수)
    long countByStatus (User.Status status);
    // 대시보드: 특정 시각 이후 가입한 회원 수 (오늘 가입자)
    long countByCreatedAtGreaterThanEqual(LocalDateTime start);

}
