package com.muckzi.muckziback.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {

    @NotBlank(message = "음식점 이름을 입력해주세요.")
    private String placeName;

    @NotBlank(message = "카테고리를 입력해주세요.")
    private String category;

    @NotBlank(message = "주소를 입력해주세요.")
    private String address;

    @NotNull(message = "위도를 입력해주세요.")
    private Double latitude;

    @NotNull(message = "경도를 입력해주세요.")
    private Double longitude;

    @NotBlank(message = "리뷰 내용을 입력해주세요.")
    @Size(max = 1000, message = "리뷰는 1000자 이내로 입력해주세요.")
    private String content;

}
