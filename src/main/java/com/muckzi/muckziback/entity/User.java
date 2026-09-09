package com.muckzi.muckziback.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User {

    @Id
    @Column(name = "USER_ID", length = 50)
    private String userId;

    @Column(name = "PASSWORD", length = 100, nullable = false)
    private String password;

    @Column(name = "NICKNAME", length = 50, nullable = false, unique = true)
    private String nickname;

    @Column(name = "ROLE", length = 20)
    private String role = "USER";  // 권한 USER, ADMIN

    @Column(name = "STATUS", length = 20)
    private String status = "ACTIVE";  // 상태 ACTIVE, DELETED, BLACK

    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

}
