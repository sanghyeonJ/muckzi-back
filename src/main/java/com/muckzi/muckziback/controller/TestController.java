package com.muckzi.muckziback.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/api/testtest")
    public String test() {
        return "인증 성공";
    }

}
