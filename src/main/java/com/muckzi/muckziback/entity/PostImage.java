package com.muckzi.muckziback.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "POST_IMAGES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_IMAGE_ID")
    private Long postImageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID", nullable = false)
    private Post post;

    @Column(name = "IMAGE_URL", nullable = false, length = 500)
    private String imageUrl;

    @Column(name = "SORT_ORDER", nullable = false)
    private Integer sortOrder;

}
