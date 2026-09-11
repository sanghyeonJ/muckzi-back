package com.muckzi.muckziback.repository;

import com.muckzi.muckziback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByNickname(String nickname);

    Optional<User> findByUserId(String userId);
}
