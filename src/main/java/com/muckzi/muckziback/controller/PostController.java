package com.muckzi.muckziback.controller;

import com.muckzi.muckziback.dto.*;
import com.muckzi.muckziback.entity.Post;
import com.muckzi.muckziback.repository.PostRepository;
import com.muckzi.muckziback.service.PostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public Page<PostListResponse> getPosts(
            @PageableDefault(size = 10) Pageable pageable
    ) {
        Pageable pageOnly = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

        return postRepository
                .findByStatusOrderByCreatedAtDesc(Post.Status.ACTIVE, pageOnly)
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
            @Valid @RequestBody PostPlaceLinkRequest request,
            Authentication authentication
    ) {
        String userId = authentication.getName();
        postService.addPlaceLinks(postId, userId, request.getPlaces());
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{postId}")
    public void updatePost (
            @PathVariable Long postId,
            @Valid @RequestBody PostRequest request,
            Authentication authentication
    ) {
        String userId = authentication.getName();

        postService.updatePost(postId, userId, request);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{postId}/images/{imageId}")
    public void deleteImage (
            @PathVariable Long postId,
            @PathVariable Long imageId,
            Authentication authentication
    ) {
        String userId = authentication.getName();

        postService.deleteImage(postId, imageId, userId);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{postId}/places/{linkId}")
    public void deletePlaceLink (
            @PathVariable Long postId,
            @PathVariable Long linkId,
            Authentication authentication
    ) {
        String userId = authentication.getName();

        postService.deletePlaceLink(postId, linkId, userId);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{postId}")
    public void deletePost (
            @PathVariable Long postId,
            Authentication authentication
    ) {
        String userId = authentication.getName();

        postService.deletePost(postId, userId);
    }

}
