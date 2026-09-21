package com.muckzi.muckziback.controller;

import com.muckzi.muckziback.dto.NicknameUpdateRequest;
import com.muckzi.muckziback.dto.PasswordUpdateRequest;
import com.muckzi.muckziback.dto.UserResponse;
import com.muckzi.muckziback.entity.User;
import com.muckzi.muckziback.repository.UserRepository;
import com.muckzi.muckziback.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

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

    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/me/nickname")
    public void updateNickname(Authentication authentication, @Valid @RequestBody NicknameUpdateRequest request) {
        String userId = authentication.getName();
        userService.updateNickname(userId, request);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/me/password")
    public void updatePassword(Authentication authentication, @Valid @RequestBody PasswordUpdateRequest request) {
        String userId = authentication.getName();
        userService.updatePassword(userId, request);
    }

}
