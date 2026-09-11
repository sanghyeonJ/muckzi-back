package com.muckzi.muckziback.service;

import com.muckzi.muckziback.dto.LoginRequest;
import com.muckzi.muckziback.dto.LoginResponse;
import com.muckzi.muckziback.dto.SignupRequest;
import com.muckzi.muckziback.entity.User;
import com.muckzi.muckziback.repository.UserRepository;
import com.muckzi.muckziback.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public void signup(SignupRequest request) {

        // 아이디 중복 확인
        if(userRepository.existsById(request.getUserId())) {
            throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
        }

        // 닉네임 중복 확인
        if(userRepository.existsByNickname(request.getNickname())){
            throw new IllegalArgumentException("이미 사용중인 닉네임입니다.");
        }

        // User생성
        User user = User.builder()
                .userId(request.getUserId())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .role(User.Role.USER)
                .status(User.Status.ACTIVE)
                .build();

        // DB 저장
        userRepository.save(user);

    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다."));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        String token = jwtTokenProvider.generateToken(user.getUserId(), user.getRole());

        return new LoginResponse(token);
    }

}
