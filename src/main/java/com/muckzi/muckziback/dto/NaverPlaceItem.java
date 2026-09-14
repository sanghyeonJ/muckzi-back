package com.muckzi.muckziback.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverPlaceItem {

    private String title;
    private String category;
    private String address;
    private String roadAddress;
    private String mapx;
    private String mapy;
}