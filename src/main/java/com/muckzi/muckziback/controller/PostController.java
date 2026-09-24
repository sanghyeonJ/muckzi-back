package com.muckzi.muckziback.controller;

import com.muckzi.muckziback.dto.PlaceIdsRequest;
import com.muckzi.muckziback.dto.PostDetailResponse;
import com.muckzi.muckziback.dto.PostListResponse;
import com.muckzi.muckziback.dto.PostRequest;
import com.muckzi.muckziback.entity.Post;
import com.muckzi.muckziback.repository.PostRepository;
import com.muckzi.muckziback.service.PostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    @GetMapping
    public Page<PostListResponse> getPosts (
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return postRepository
                .findByStatusOrderByCreatedAtDesc(Post.Status.ACTIVE, pageable)
                .map(PostListResponse::new);
    }

    @GetMapping("/{postId}")
    public PostDetailResponse getPostDetail (@PathVariable Long postId) {
        return postService.getPostDetail(postId);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public Map<String, Long> createPost(
            @Valid @RequestBody PostRequest request,
            Authentication authentication
    ) {
        String userId = authentication.getName();
        Post post = postService.createPost(userId, request);

        return Map.of("postId", post.getPostId());
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(value = "/{postId}/images", consumes = "multipart/form-data")
    public void addImages (
            @PathVariable Long postId,
            @RequestParam("files") List<MultipartFile> files,
            Authentication authentication
    ) {
        String userId = authentication.getName();
        postService.addImages(postId, userId, files);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/{postId}/places")
    public void addPlaceLinks (
            @PathVariable Long postId,
            @Valid @RequestBody PlaceIdsRequest request,
            Authentication authentication
    ) {
        String userId = authentication.getName();
        postService.addPlaceLinks(postId, userId, request.getPlaceIds());
    }

}
