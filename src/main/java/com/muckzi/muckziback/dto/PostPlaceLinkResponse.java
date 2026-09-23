package com.muckzi.muckziback.dto;

import com.muckzi.muckziback.entity.PostPlaceLink;
import lombok.Getter;

@Getter
public class PostPlaceLinkResponse {

    private final Long postPlaceLinkId;
    private final Long placeId;
    private final String placeName;

    public PostPlaceLinkResponse (PostPlaceLink postPlaceLink) {
        this.postPlaceLinkId = postPlaceLink.getPostPlaceLinkId();
        this.placeId = postPlaceLink.getPlace().getPlaceId();
        this.placeName = postPlaceLink.getPlace().getPlaceName();
    }

}
