package com.muckzi.muckziback.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookmarkRequest {

    @NotBlank(message = "음식점 이름을 입력해주세요.")
    private String placeName;

    @NotBlank(message = "카테고리를 입력해주세요.")
    private String category;

    private String filterCategory;

    @NotBlank(message = "주소를 입력해주세요.")
    private String address;

    @NotNull(message = "위도를 입력해주세요.")
    private Double latitude;

    @NotNull(message = "경도를 입력해주세요.")
    private Double longitude;

}
