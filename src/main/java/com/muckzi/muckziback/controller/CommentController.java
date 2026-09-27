package com.muckzi.muckziback.controller;

import com.muckzi.muckziback.dto.CommentRequest;
import com.muckzi.muckziback.dto.CommentResponse;
import com.muckzi.muckziback.service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/posts/{postId}/comments")
    public List<CommentResponse> getComments (@PathVariable Long postId) {
        return commentService.getComments(postId);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/posts/{postId}/comments")
    public CommentResponse createComment (
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequest request,
            Authentication authentication
    ) {
        String userId = authentication.getName();

        return commentService.createComment(postId, userId, request);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/comments/{commentId}")
    public void updateComment (
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequest request,
            Authentication authentication
    ) {
        String userId = authentication.getName();

        commentService.updateComment(commentId, userId, request);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/comments/{commentId}")
    public void deleteComment (
            @PathVariable Long commentId,
            Authentication authentication
    ) {
        String userId = authentication.getName();

        commentService.deleteComment(commentId, userId);
    }

}
