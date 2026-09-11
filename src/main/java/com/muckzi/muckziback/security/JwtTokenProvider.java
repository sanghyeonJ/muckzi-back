package com.muckzi.muckziback.security;

import com.muckzi.muckziback.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final long expiration;

    public JwtTokenProvider(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") long expiration) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }

    public String generateToken(String userId, User.Role role) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(userId)
                .claim("role", role.name())
                .issuedAt(now)
                .expiration(expiry)
                .signWith(secretKey)
                .compact();
    }

}
