package com.muckzi.muckziback.dto;

import com.muckzi.muckziback.entity.Comment;
import com.muckzi.muckziback.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminCommentResponse {

    private final Long commentId;
    private final Long parentId;
    private final Long postId;
    private final String postTitle;
    private final Post.Status postStatus;
    private final String userId;
    private final String nickname;
    private final String content;
    private final Comment.Status status;
    private final LocalDateTime createdAt;

    public AdminCommentResponse (Comment comment) {
        this.commentId = comment.getCommentId();
        this.parentId = comment.getParent() != null ?
                comment.getParent().getCommentId() :
                null;
        this.postId = comment.getPost().getPostId();
        this.postTitle = comment.getPost().getTitle();
        this.postStatus = comment.getPost().getStatus();
        this.userId = comment.getUser().getUserId();
        this.nickname = comment.getUser().getNickname();
        this.content = comment.getContent();
        this.status = comment.getStatus();
        this.createdAt = comment.getCreatedAt();
    }

}
