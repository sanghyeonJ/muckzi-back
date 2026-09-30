package com.muckzi.muckziback.dto;

import com.muckzi.muckziback.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminPostResponse {

    private final Long postId;
    private final String title;
    private final String userId;
    private final String nickname;
    private final Post.Status status;
    private final LocalDateTime createdAt;

    public AdminPostResponse(Post post) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.userId = post.getUser().getUserId();
        this.nickname = post.getUser().getNickname();
        this.status = post.getStatus();
        this.createdAt = post.getCreatedAt();
    }

}
