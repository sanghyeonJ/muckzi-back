package com.muckzi.muckziback.dto;

import com.muckzi.muckziback.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {

    private String userId;
    private String password;
    private User.Role role;
    private User.Status status;

}
