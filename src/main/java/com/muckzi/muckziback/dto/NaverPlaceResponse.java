package com.muckzi.muckziback.dto;

import lombok.Getter;

@Getter
public class NaverPlaceResponse {

    private final String title;
    private final String category;
    private final String address;
    private final String roadAddress;
    private final Double latitude;
    private final Double longitude;

    public NaverPlaceResponse(
            String title,
            String category,
            String address,
            String roadAddress,
            Double latitude,
            Double longitude
    ) {
        this.title = title;
        this.category = category;
        this.address = address;
        this.roadAddress = roadAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}