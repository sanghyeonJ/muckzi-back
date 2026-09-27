package com.muckzi.muckziback.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class MuckziPlaceSearchResponse {

    private final List<MuckziPlaceResponse> places;
    private final boolean isEnd;

    public MuckziPlaceSearchResponse (List<MuckziPlaceResponse> places, boolean isEnd) {
        this.places = places;
        this.isEnd = isEnd;
    }

}
