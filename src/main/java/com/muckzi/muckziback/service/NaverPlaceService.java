package com.muckzi.muckziback.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

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

    public String searchPlaces(String query) {

        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/v1/local")
                        .queryParam("query", query)
                        .queryParam("display", 5)
                        .build())
                .header("X-NCP-APIGW-API-KEY-ID", keyId)
                .header("X-NCP-APIGW-API-KEY", key)
                .retrieve()
                .body(String.class);
    }
}