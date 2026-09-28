package com.muckzi.muckziback.dto;

import com.muckzi.muckziback.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminUserResponse {

    private final String userId;
    private final String nickname;
    private final User.Role role;
    private final User.Status status;
    private final LocalDateTime createdAt;

    public AdminUserResponse(User user) {
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.role = user.getRole();
        this.status = user.getStatus();
        this.createdAt = user.getCreatedAt();
    }

}
