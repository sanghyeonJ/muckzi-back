package com.muckzi.muckziback.controller;

import com.muckzi.muckziback.dto.AdminPostResponse;
import com.muckzi.muckziback.entity.Post;
import com.muckzi.muckziback.service.AdminPostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/posts")
@RequiredArgsConstructor
public class AdminPostController {

    private final AdminPostService adminPostService;

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public Page<AdminPostResponse> getPosts (
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Post.Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return adminPostService.getPages(keyword, status, page, size);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/{postId}/status")
    public void updatePostStatus (
            @PathVariable Long postId,
            @RequestParam Post.Status status
    ) {
        adminPostService.updatePostStatus(postId, status);
    }

}
