package com.muckzi.muckziback.repository;

import com.muckzi.muckziback.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void 사용자_저장_테스트() {
        User user = User.builder()
                .userId("test01")
                .password("1234")
                .nickname("테스트회원")
                .build();

        User savedUser = userRepository.save(user);

        System.out.println("저장된 회원 ID: " + savedUser.getUserId());
        System.out.println("저장된 닉네임: " + savedUser.getNickname());
        System.out.println("저장된 권한: " + savedUser.getRole());
        System.out.println("저장된 상태: " + savedUser.getStatus());
    }

}
