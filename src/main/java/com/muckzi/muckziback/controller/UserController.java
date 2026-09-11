package com.muckzi.muckziback.controller;

import com.muckzi.muckziback.dto.UserResponse;
import com.muckzi.muckziback.entity.User;
import com.muckzi.muckziback.repository.UserRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/me")
    public UserResponse getMyInfo(Authentication authentication) {
        String userId = authentication.getName();

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        return new UserResponse(
                user.getUserId(),
                user.getNickname(),
                user.getRole(),
                user.getStatus()
        );
    }

}
