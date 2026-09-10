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

    public enum Role{
        USER,
        ADMIN
    }
    public enum Status {
        ACTIVE,
        DELETED,
        BLACK
    }

    @Id
    @Column(name = "USER_ID", length = 50)
    private String userId;

    @Column(name = "PASSWORD", length = 100, nullable = false)
    private String password;

    @Column(name = "NICKNAME", length = 50, nullable = false, unique = true)
    private String nickname;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", length = 20, nullable = false)
    private Role role = Role.USER;  // 권한 USER, ADMIN

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 20, nullable = false)
    private Status status = Status.ACTIVE;  // 상태 ACTIVE, DELETED, BLACK

    @Builder.Default
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

}
