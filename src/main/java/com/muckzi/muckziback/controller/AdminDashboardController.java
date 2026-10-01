package com.muckzi.muckziback.controller;

import com.muckzi.muckziback.dto.AdminDashboardResponse;
import com.muckzi.muckziback.service.AdminDashboardService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public AdminDashboardResponse getDashboard () {
        return adminDashboardService.getDashboard();
    }

}
