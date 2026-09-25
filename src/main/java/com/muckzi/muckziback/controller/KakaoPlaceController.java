package com.muckzi.muckziback.controller;

import com.muckzi.muckziback.dto.MuckziPlaceResponse;
import com.muckzi.muckziback.service.KakaoPlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/kakao/places")
@RequiredArgsConstructor
public class KakaoPlaceController {

    private final KakaoPlaceService kakaoPlaceService;

    @GetMapping
    public List<MuckziPlaceResponse> searchPlaces(
            @RequestParam String query,
            @RequestParam Double swLat,
            @RequestParam Double swLng,
            @RequestParam Double neLat,
            @RequestParam Double neLng
    ) {
        return kakaoPlaceService.searchPlaces(
                query,
                swLat,
                swLng,
                neLat,
                neLng
        );
    }

    @GetMapping("/search")
    public List<MuckziPlaceResponse> searchPlacesNationwide(
            @RequestParam String query
    ) {
        return kakaoPlaceService.searchPlacesNationwide(query);
    }
}