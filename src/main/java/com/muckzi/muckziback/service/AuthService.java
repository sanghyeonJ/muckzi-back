package com.muckzi.muckziback.service;

import com.muckzi.muckziback.dto.LoginRequest;
import com.muckzi.muckziback.dto.LoginResponse;
import com.muckzi.muckziback.dto.RefreshRequest;
import com.muckzi.muckziback.dto.SignupRequest;
import com.muckzi.muckziback.entity.RefreshToken;
import com.muckzi.muckziback.entity.User;
import com.muckzi.muckziback.repository.RefreshTokenRepository;
import com.muckzi.muckziback.repository.UserRepository;
import com.muckzi.muckziback.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
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

        if(user.getStatus() != User.Status.ACTIVE){
            throw new IllegalArgumentException("로그인할 수 없는 계정입니다.");
        }

        String token = jwtTokenProvider.generateToken(user.getUserId(), user.getRole());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getUserId());

        // 기존 리프레시 토큰이 있으면 갱신, 없으면 새로 생성
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByUserId(user.getUserId())
                .orElse(RefreshToken.builder().userId(user.getUserId()).build());

        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setExpiryDate(
                LocalDateTime.now().plusSeconds(jwtTokenProvider.getRefreshExpiration() / 1000)
        );

        refreshTokenRepository.save(refreshTokenEntity);

        return new LoginResponse(token, refreshToken);
    }

    public LoginResponse refresh(RefreshRequest request) {
        String refreshToken = request.getRefreshToken();

        // 1. 토큰 자체의 서명/만료 검증
        if (!jwtTokenProvider.isValidToken(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.");
        }

        String userId = jwtTokenProvider.getUserId(refreshToken);

        // 2. DB에 저장된 리프레시 토큰과 일치하는지 확인
        RefreshToken savedRefreshToken = refreshTokenRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("로그인이 필요합니다."));

        if (!savedRefreshToken.getToken().equals(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.");
        }

        // 3. 유저 정보 조회 (role 필요)
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        if (user.getStatus() != User.Status.ACTIVE) {
            throw new IllegalArgumentException("로그인할 수 없는 계정입니다.");
        }

        // 4. 새 액세스 토큰만 발급 (리프레시 토큰은 재사용)
        String newAccessToken = jwtTokenProvider.generateToken(user.getUserId(), user.getRole());

        return new LoginResponse(newAccessToken, refreshToken);
    }

}
