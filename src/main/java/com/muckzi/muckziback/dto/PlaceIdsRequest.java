package com.muckzi.muckziback.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlaceIdsRequest {

    @NotEmpty(message = "연결할 음식점을 선택해주세요.")
    private List<Long> placeIds;

}
