package com.muckzi.muckziback.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverPlaceSearchResponse {

    private List<NaverPlaceItem> items;
}