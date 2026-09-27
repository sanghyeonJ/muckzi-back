package com.muckzi.muckziback.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class KakaoPlaceSearchResponse {

    private List<KakaoPlaceItem> documents;
    private Meta meta;

    @Getter
    @NoArgsConstructor
    public static class Meta {
        private Integer total_count;
        private Integer pageable_count;
        private Boolean is_end;
    }

}
