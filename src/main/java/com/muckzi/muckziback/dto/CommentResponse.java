package com.muckzi.muckziback.dto;

import com.muckzi.muckziback.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CommentResponse {

    private final Long commentId;
    private final Long parentId;
    private final String userId;
    private final String nickname;
    private final String content;
    private final boolean deleted;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final List<CommentResponse> replies = new ArrayList<>();

    public CommentResponse(Comment comment) {
        this.commentId = comment.getCommentId();
        this.parentId = comment.getParent() != null ?
                comment.getParent().getCommentId() : null;
        this.deleted = comment.getStatus() == Comment.Status.DELETED;

        // 삭제된 댓글이면 작성자와 내용을 숨김
        if (this.deleted) {
            this.userId = null;
            this.nickname = null;
            this.content = "삭제된 댓글입니다.";
        } else {
            this.userId = comment.getUser().getUserId();
            this.nickname = comment.getUser().getDisplayNickname();
            this.content = comment.getContent();
        }

        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }


}
