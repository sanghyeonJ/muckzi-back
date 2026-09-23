package com.muckzi.muckziback.dto;

import com.muckzi.muckziback.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostListResponse {

    private final Long postId;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;

    public PostListResponse(Post post){
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
    }

}
