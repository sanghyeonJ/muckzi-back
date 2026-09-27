package com.muckzi.muckziback.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostPlaceLinkRequest {

    @NotEmpty(message = "연결할 음식점을 선택해주세요.")
    private List<@Valid PlaceInfo> places;

    @Getter
    @Setter
    public static class PlaceInfo {
        private Long placeId;
        private String placeName;
        private String category;
        private String filterCategory;
        private String address;
        private Double latitude;
        private Double longitude;
    }

}
