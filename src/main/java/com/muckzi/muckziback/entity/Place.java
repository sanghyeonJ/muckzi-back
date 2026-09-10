package com.muckzi.muckziback.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PLACES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 자동증가 옵션 매핑
    @Column(name = "PLACE_ID", unique = true)
    private Long placeId;

    @Column(name = "NAVER_MAP_ID", nullable = false, length = 100)
    private String naverMapId;

    @Column(name = "PLACE_NAME", nullable = false, length = 100)
    private String placeName;

    @Column(name = "CATEGORY", nullable = false, length = 30)
    private String category;

    @Column(name = "ADDRESS", nullable = false, length = 200)
    private String address;

    @Column(name = "LATITUDE", columnDefinition = "NUMBER(13,10)", nullable = false)
    private Double latitude;

    @Column(name = "LONGITUDE", columnDefinition = "NUMBER(13,10)", nullable = false)
    private Double longitude;

    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createAt = LocalDateTime.now();

}
