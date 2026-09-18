package com.muckzi.muckziback.dto;

import lombok.Getter;

@Getter
public class MuckziPlaceResponse {

    private final String kakaoPlaceId;
    private final String placeName;
    private final String category;
    private final String filterCategory;
    private final String address;
    private final Double latitude;
    private final Double longitude;

    public MuckziPlaceResponse(
            String kakaoPlaceId,
            String placeName,
            String category,
            String filterCategory,
            String address,
            Double latitude,
            Double longitude
    ) {
        this.kakaoPlaceId = kakaoPlaceId;
        this.placeName = placeName;
        this.category = category;
        this.filterCategory = filterCategory;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}