package com.muckzi.muckziback.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.muckzi.muckziback.dto.NaverPlaceItem;
import com.muckzi.muckziback.dto.NaverPlaceResponse;
import com.muckzi.muckziback.dto.NaverPlaceSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NaverPlaceService {

    @Value("${naver.api.key-id}")
    private String keyId;

    @Value("${naver.api.key}")
    private String key;

    private final RestClient restClient = RestClient.builder()
            .baseUrl("https://naverapihub.apigw.ntruss.com")
            .build();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<NaverPlaceResponse> searchPlaces(String query) {

        String json = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/v1/local")
                        .queryParam("query", query)
                        .queryParam("display", 5)
                        .build())
                .header("X-NCP-APIGW-API-KEY-ID", keyId)
                .header("X-NCP-APIGW-API-KEY", key)
                .retrieve()
                .body(String.class);

        try {
            NaverPlaceSearchResponse response =
                    objectMapper.readValue(json, NaverPlaceSearchResponse.class);

            return response.getItems().stream()
                    .map(item -> new NaverPlaceResponse(
                            item.getTitle(),
                            item.getCategory(),
                            item.getAddress(),
                            item.getRoadAddress(),
                            Double.parseDouble(item.getMapy()) / 10_000_000,
                            Double.parseDouble(item.getMapx()) / 10_000_000
                    ))
                    .toList();

        } catch (Exception e) {
            throw new RuntimeException("네이버 장소 검색 응답 변환에 실패했습니다.", e);
        }
    }
}