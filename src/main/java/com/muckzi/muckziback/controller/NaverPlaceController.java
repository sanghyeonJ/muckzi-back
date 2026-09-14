package com.muckzi.muckziback.controller;

import com.muckzi.muckziback.dto.NaverPlaceResponse;
import com.muckzi.muckziback.service.NaverPlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/naver/places")
@RequiredArgsConstructor
public class NaverPlaceController {

    private final NaverPlaceService naverPlaceService;

    @GetMapping
    public List<NaverPlaceResponse> searchPlaces(
            @RequestParam String query
    ) {
        return naverPlaceService.searchPlaces(query);
    }
}