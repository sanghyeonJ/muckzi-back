package com.muckzi.muckziback.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class KakaoPlaceSearchResponse {

    private List<KakaoPlaceItem> documents;

}
