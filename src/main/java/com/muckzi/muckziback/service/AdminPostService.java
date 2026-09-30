package com.muckzi.muckziback.service;

import com.muckzi.muckziback.dto.AdminPostResponse;
import com.muckzi.muckziback.entity.Post;
import com.muckzi.muckziback.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminPostService {

    private final PostRepository postRepository;

    // 게시글 목록 조회
    @Transactional(readOnly = true)
    public Page<AdminPostResponse> getPages (String keyword, Post.Status status, int page, int size) {
        if (keyword != null && keyword.isBlank()) {
            keyword = null;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        return postRepository.searchPost(keyword, status, pageable)
                .map(AdminPostResponse::new);
    }

    // 게시글 상태 변경 (삭제 / 복구)
    @Transactional
    public void updatePostStatus (Long postId, Post.Status status) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        if (post.getStatus() == status) {
            throw new IllegalArgumentException(
                    status == Post.Status.DELETED ? "이미 삭제된 게시글입니다." : "이미 정상 상태인 게시글입니다."
            );
        }

        post.setStatus(status);
    }

}
