package com.muckzi.muckziback.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminDashboardResponse {

    private final long totalUsers;
    private final long todayUsers;
    private final long blackUsers;

    private final long totalPosts;
    private final long todayPosts;

    private final long totalReviews;
    private final long todayReviews;

    private final long totalComments;

}
