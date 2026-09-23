package com.muckzi.muckziback.dto;

import com.muckzi.muckziback.entity.PostImage;
import lombok.Getter;

@Getter
public class PostImageResponse {

    private final Long postImageId;
    private final String imageUrl;
    private final Integer sortOrder;

    public PostImageResponse (PostImage postImage) {
        this.postImageId = postImage.getPostImageId();
        this.imageUrl = postImage.getImageUrl();
        this.sortOrder = postImage.getSortOrder();
    }

}
