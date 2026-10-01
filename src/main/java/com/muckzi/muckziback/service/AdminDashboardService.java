package com.muckzi.muckziback.service;

import com.muckzi.muckziback.dto.AdminDashboardResponse;
import com.muckzi.muckziback.entity.Comment;
import com.muckzi.muckziback.entity.Post;
import com.muckzi.muckziback.entity.Review;
import com.muckzi.muckziback.entity.User;
import com.muckzi.muckziback.repository.CommentRepository;
import com.muckzi.muckziback.repository.PostRepository;
import com.muckzi.muckziback.repository.ReviewRepository;
import com.muckzi.muckziback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public AdminDashboardResponse getDashboard () {
        LocalDateTime todayStart = LocalDate.now().atStartOfDay();

        return AdminDashboardResponse.builder()
                .totalUsers(userRepository.countByStatusNot(User.Status.DELETED))
                .todayUsers(userRepository.countByCreatedAtGreaterThanEqual(todayStart))
                .blackUsers(userRepository.countByStatus(User.Status.BLACK))

                .totalPosts(postRepository.countByStatus(Post.Status.ACTIVE))
                .todayPosts(postRepository.countByStatusAndCreatedAtGreaterThanEqual(Post.Status.ACTIVE, todayStart))

                .totalReviews(reviewRepository.countByStatus(Review.Status.ACTIVE))
                .todayReviews(reviewRepository.countByStatusAndCreatedAtGreaterThanEqual(Review.Status.ACTIVE, todayStart))

                .totalComments(commentRepository.countByStatus(Comment.Status.ACTIVE))
                .build();
    }

}
