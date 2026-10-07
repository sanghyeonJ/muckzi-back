package com.muckzi.muckziback.service;

import com.muckzi.muckziback.dto.AdminCommentResponse;
import com.muckzi.muckziback.entity.Comment;
import com.muckzi.muckziback.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminCommentService {

    private final CommentRepository commentRepository;
    private final CommentService commentService;

    // 댓글 목록 조회 (검색 + 상태 필터 + 페이지네이션)
    @Transactional(readOnly = true)
    public Page<AdminCommentResponse> getComments (String keyword, Comment.Status status, int page, int size) {
        keyword = (keyword == null) ? "" : keyword.trim();

        // 작성일 최신순 정렬
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        return commentRepository.searchComments(keyword, status, pageable)
                .map(AdminCommentResponse::new);
    }

    // 댓글 삭제 (사용자 삭제와 같은 규칙 적용)
    public void deleteComment (Long commentId) {
        commentService.deleteCommentByAdmin(commentId);
    }

}
