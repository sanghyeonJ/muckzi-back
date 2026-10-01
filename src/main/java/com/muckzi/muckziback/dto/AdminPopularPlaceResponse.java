package com.muckzi.muckziback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminPopularPlaceResponse {

    private final Long placeId;
    private final String placeName;
    private final Long reviewCount;

}
