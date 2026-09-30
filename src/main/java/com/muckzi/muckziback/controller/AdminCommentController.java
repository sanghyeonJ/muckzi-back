package com.muckzi.muckziback.controller;

import com.muckzi.muckziback.dto.AdminCommentResponse;
import com.muckzi.muckziback.entity.Comment;
import com.muckzi.muckziback.service.AdminCommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/comments")
@RequiredArgsConstructor
public class AdminCommentController {

    private final AdminCommentService adminCommentService;

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public Page<AdminCommentResponse> getComments (
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Comment.Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return adminCommentService.getComments(keyword, status, page, size);
    }


    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{commentId}")
    public void deleteComment (@PathVariable Long commentId) {
        adminCommentService.deleteComment(commentId);
    }

}
