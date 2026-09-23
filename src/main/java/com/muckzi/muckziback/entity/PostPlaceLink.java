package com.muckzi.muckziback.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "POST_PLACE_LINKS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostPlaceLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_PLACE_LINK_ID")
    private Long postPlaceLinkId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLACE_ID", nullable = false)
    private Place place;

}
