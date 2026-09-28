package com.muckzi.muckziback.controller;

import com.muckzi.muckziback.dto.AdminUserResponse;
import com.muckzi.muckziback.entity.User;
import com.muckzi.muckziback.service.AdminUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public Page<AdminUserResponse> getUsers (
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) User.Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return adminUserService.getUsers(keyword, status, page, size);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/{userId}/status")
    public void updateUserStatus (
            @PathVariable String userId,
            @RequestParam User.Status status
    ) {
        adminUserService.updateUserStatus(userId, status);
    }


}
