package com.muckzi.muckziback.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "REFRESH_TOKENS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @Column(name = "USER_ID", length = 50)
    private String userId;

    @Column(name = "TOKEN", length = 500, nullable = false)
    private String token;

    @Column(name = "EXPIRY_DATE", nullable = false)
    private LocalDateTime expiryDate;

}
