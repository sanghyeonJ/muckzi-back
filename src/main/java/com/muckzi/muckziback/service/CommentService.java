package com.muckzi.muckziback.service;

import com.muckzi.muckziback.dto.CommentRequest;
import com.muckzi.muckziback.dto.CommentResponse;
import com.muckzi.muckziback.entity.Comment;
import com.muckzi.muckziback.entity.Post;
import com.muckzi.muckziback.entity.User;
import com.muckzi.muckziback.repository.CommentRepository;
import com.muckzi.muckziback.repository.PostRepository;
import com.muckzi.muckziback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 댓글 목록 조회
    @Transactional(readOnly = true)
    public List<CommentResponse> getComments (Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        if (post.getStatus() != Post.Status.ACTIVE) {
            throw new IllegalArgumentException("삭제된 게시글입니다.");
        }

        List<Comment> comments = commentRepository.findAllByPostIdWithUser(postId);

        // 댓글 id -> 댓글 응답
        Map<Long, CommentResponse> commentMap = new LinkedHashMap<>();

        for (Comment comment : comments) {
            CommentResponse response = new CommentResponse(comment);

            if (comment.getParent() == null) {
                // 일반댓글
                commentMap.put(comment.getCommentId(), response);
            } else {
                CommentResponse parent = commentMap.get(comment.getParent().getCommentId());
                if (parent != null) {
                    parent.getReplies().add(response);
                }
            }
        }

        return new ArrayList<>(commentMap.values());
    }

    // 댓글 답글 작성
    @Transactional
    public CommentResponse createComment (Long postId, String userId, CommentRequest request) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다."));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        if (post.getStatus() != Post.Status.ACTIVE) {
            throw new IllegalArgumentException("삭제된 게시글에는 댓글을 작성할 수 없습니다.");
        }

        Comment parent = null;

        if (request.getParentId() != null) {
            parent = commentRepository.findById(request.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모댓글을 찾을 수 없습니다."));
            if (!parent.getPost().getPostId().equals(postId)) {
                throw new IllegalArgumentException("해당 게시글의 댓글이 아닙니다.");
            }
            if (parent.getParent() != null) {
                throw new IllegalArgumentException("답글에는 답글을 작성할 수 없습니다.");
            }
            if (parent.getStatus() != Comment.Status.ACTIVE) {
                throw new IllegalArgumentException("삭제된 댓글에는 답글을 작성할 수 없습니다.");
            }
        }

        Comment comment = Comment.builder()
                .post(post)
                .user(user)
                .parent(parent)
                .content(request.getContent())
                .build();

        return new CommentResponse(commentRepository.save(comment));
    }

    // 댓글 수정
    @Transactional
    public void updateComment (Long commentId, String userId, CommentRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        if (comment.getStatus() != Comment.Status.ACTIVE) {
            throw new IllegalArgumentException("수정할 수 없는 댓글입니다.");
        }
        if (!comment.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("본인이 작성한 댓글만 수정할 수 있습니다.");
        }

        comment.setContent(request.getContent());
        comment.setUpdatedAt(LocalDateTime.now());
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment (Long commentId, String userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        if (comment.getStatus() != Comment.Status.ACTIVE) {
            throw new IllegalArgumentException("이미 삭제된 댓글입니다.");
        }
        if (!comment.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("본인이 작성한 댓글만 삭제할 수 있습니다.");
        }

        if (comment.getParent() == null
                && commentRepository.existsByParent_CommentIdAndStatus(commentId, Comment.Status.ACTIVE)){
            comment.setStatus(Comment.Status.DELETED);
            return;
        }

        Comment parent = comment.getParent();
        commentRepository.delete(comment);

        if (parent != null
                && parent.getStatus() == Comment.Status.DELETED
                && !commentRepository.existsByParent_CommentIdAndStatus(parent.getCommentId(), Comment.Status.ACTIVE)){
            commentRepository.delete(parent);
        }
    }

}
