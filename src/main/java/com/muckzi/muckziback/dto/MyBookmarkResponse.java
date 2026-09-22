package com.muckzi.muckziback.dto;

import com.muckzi.muckziback.entity.Bookmark;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyBookmarkResponse {

    private final Long bookmarkId;
    private final Long placeId;
    private final String placeName;
    private final String filterCategory;
    private final String address;
    private final LocalDateTime createdAt;

    public MyBookmarkResponse(Bookmark bookmark) {
        this.bookmarkId = bookmark.getBookmarkId();
        this.placeId = bookmark.getPlace().getPlaceId();
        this.placeName = bookmark.getPlace().getPlaceName();
        this.filterCategory = bookmark.getPlace().getFilterCategory();
        this.address = bookmark.getPlace().getAddress();
        this.createdAt = bookmark.getCreatedAt();
    }

}
