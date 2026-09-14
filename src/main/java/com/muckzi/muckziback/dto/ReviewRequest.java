package com.muckzi.muckziback.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {

    @NotBlank(message = "리뷰 내용을 입력해주세요.")
    @Size(max = 1000, message = "리뷰는 1000자 이내로 입력해주세요.")
    private String content;

}
