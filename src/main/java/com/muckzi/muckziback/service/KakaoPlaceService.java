package com.muckzi.muckziback.service;

import com.muckzi.muckziback.dto.KakaoPlaceSearchResponse;
import com.muckzi.muckziback.dto.MuckziPlaceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KakaoPlaceService {

    @Value("${kakao.rest-api-key}")
    private String kakaoRestApiKey;

    private final RestClient restClient = RestClient.builder()
            .baseUrl("https://dapi.kakao.com")
            .build();

    public List<MuckziPlaceResponse> searchPlaces(
            String query,
            Double swLat,
            Double swLng,
            Double neLat,
            Double neLng
    ) {

        String rect = swLng + "," + swLat + "," + neLng + "," + neLat;

        KakaoPlaceSearchResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2/local/search/keyword.json")
                        .queryParam("query", query)
                        .queryParam("rect", rect)
                        .build()
                )
                .header(
                        HttpHeaders.AUTHORIZATION,
                        "KakaoAK " + kakaoRestApiKey
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(KakaoPlaceSearchResponse.class);

        return response.getDocuments().stream()

                // 음식점과 카페만 남긴다.
                .filter(item ->
                        "FD6".equals(item.getCategory_group_code())
                                || "CE7".equals(item.getCategory_group_code())
                )

                .map(item -> {

                    String category = item.getCategory_name();

                    String filterCategory;

                    if ("CE7".equals(item.getCategory_group_code())) {
                        // 카페는 카카오 대분류 자체가 카페이므로 그대로 사용한다.
                        filterCategory = "카페";

                    } else {
                        // 음식점은 상세 카테고리에서 대표 카테고리를 추출한다.
                        String kakaoCategory = category
                                .replaceFirst("^음식점\\s*>\\s*", "")
                                .split("\\s*>\\s*")[0];

                        filterCategory = convertCategory(kakaoCategory);
                    }

                    String address = !item.getRoad_address_name().isBlank()
                            ? item.getRoad_address_name()
                            : item.getAddress_name();

                    return new MuckziPlaceResponse(
                            item.getId(),
                            item.getPlace_name(),
                            category,
                            filterCategory,
                            address,
                            Double.parseDouble(item.getY()),
                            Double.parseDouble(item.getX())
                    );
                })
                .toList();
    }

    private String convertCategory(String category) {

        return switch (category) {
            case "한식" -> "한식";
            case "일식" -> "일식";
            case "중식" -> "중식";
            case "양식" -> "양식";
            case "술집", "주점" -> "술집";
            case "치킨" -> "치킨";
            case "분식" -> "분식";
            case "패스트푸드" -> "패스트푸드";
            default -> "기타";
        };
    }
}