package com.muckzi.muckziback.dto;

import com.muckzi.muckziback.entity.Place;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaceResponse {

    private Long placeId;
    private String placeName;
    private String category;
    private String filterCategory;
    private String address;
    private Double latitude;
    private Double longitude;

    public PlaceResponse(Place place) {
        this.placeId = place.getPlaceId();
        this.placeName = place.getPlaceName();
        this.category = place.getCategory();
        this.filterCategory = place.getFilterCategory();
        this.address = place.getAddress();
        this.latitude = place.getLatitude();
        this.longitude = place.getLongitude();
    }

}
