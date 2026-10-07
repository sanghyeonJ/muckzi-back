package com.muckzi.muckziback.service;

import com.muckzi.muckziback.dto.AdminUserResponse;
import com.muckzi.muckziback.entity.User;
import com.muckzi.muckziback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;

    // 회원 목록 조회
    @Transactional(readOnly = true)
    public Page<AdminUserResponse> getUsers (String keyword, User.Status status, int page, int size) {
        keyword = (keyword == null) ? "" : keyword.trim();

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        return userRepository.searchUsers(keyword, status, pageable)
                .map(AdminUserResponse::new);
    }

    // 회원 상태 변경
    @Transactional
    public void updateUserStatus (String userId, User.Status status) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다."));

        if (user.getRole() == User.Role.ADMIN) {
            throw new IllegalArgumentException("관리자 계정의 상태는 변경할 수 없습니다.");
        }
        if (user.getStatus() == User.Status.DELETED) {
            throw new IllegalArgumentException("탈퇴한 회원의 상태는 변경할 수 없습니다.");
        }
        if (status != User.Status.ACTIVE && status != User.Status.BLACK) {
            throw new IllegalArgumentException("변경할 수 없는 상태입니다.");
        }

        user.setStatus(status);
    }

}
