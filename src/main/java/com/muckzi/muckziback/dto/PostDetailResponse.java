package com.muckzi.muckziback.dto;

import com.muckzi.muckziback.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostDetailResponse {

    private final Long postId;
    private final String userId;
    private final String nickname;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final List<PostImageResponse> images;
    private final List<PostPlaceLinkResponse> places;

    public PostDetailResponse (
            Post post,
            List<PostImageResponse> images,
            List<PostPlaceLinkResponse> places
    ) {
        this.postId = post.getPostId();
        this.userId = post.getUser().getUserId();
        this.nickname = post.getUser().getNickname();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.images = images;
        this.places = places;
    }

}
