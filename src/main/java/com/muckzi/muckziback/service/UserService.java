package com.muckzi.muckziback.service;

import com.muckzi.muckziback.dto.NicknameUpdateRequest;
import com.muckzi.muckziback.dto.PasswordUpdateRequest;
import com.muckzi.muckziback.entity.User;
import com.muckzi.muckziback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void updateNickname(String userId, NicknameUpdateRequest request) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        if(!user.getNickname().equals(request.getNickname())
                && userRepository.existsByNickname(request.getNickname())){
            throw new IllegalArgumentException("이미 사용중인 닉네임입니다.");
        }
        user.setNickname(request.getNickname());
    }

    @Transactional
    public void updatePassword(String userId, PasswordUpdateRequest request) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    }

}
