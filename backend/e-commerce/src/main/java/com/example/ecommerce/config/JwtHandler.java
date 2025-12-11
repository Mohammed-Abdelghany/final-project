package com.example.ecommerce.config;

import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.helper.JwtToken;
import com.example.ecommerce.mapper.UserMapper;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repo.UserRepo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;  // ✅ استخدم SecretKey
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtHandler {
    private final JwtToken jwtToken;
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final SecretKey key;  // ✅ SecretKey بدلاً من Key
    private final JwtParser jwtParser;

    @Autowired
    public JwtHandler(JwtToken jwtToken, UserRepo userRepo, UserMapper userMapper) {
        this.jwtToken = jwtToken;
        this.userRepo = userRepo;
        this.userMapper = userMapper;

        // ✅ SecretKey
        this.key = Keys.hmacShaKeyFor(
                jwtToken.getSecret_Key().getBytes(StandardCharsets.UTF_8)
        );

        // ✅ بناء Parser مرة واحدة
        this.jwtParser = Jwts.parser()
                .verifyWith(key)
                .build();
    }

    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = Date.from(now.toInstant().plus(jwtToken.getDuration()));

        return Jwts.builder()
                .subject(user.getUsername())       // ✅ بدون set
                .issuedAt(now)                     // ✅ بدون set
                .expiration(expiryDate)            // ✅ بدون set
                .claim("roles", user.getRoles())
                .signWith(key)                     // ✅ إضافة signWith
                .compact();
    }

    public UserDto validateToken(String token) {
        try {
            // ✅ parseSignedClaims بدلاً من parseClaimsJws
            Claims claims = jwtParser
                    .parseSignedClaims(token)
                    .getPayload();  // ✅ getPayload بدلاً من getBody

            String username = claims.getSubject();

            User user = userRepo.findByUsername(username);
            if (user == null) {
                throw new RuntimeException("token.invalid");
            }

            return userMapper.userToUserDto(user);

        } catch (ExpiredJwtException e) {
            throw new RuntimeException("token.expired");
        } catch (JwtException e) {
            throw new RuntimeException("token.invalid");
        }
    }

    public String extractUsername(String token) {
        try {
            return jwtParser
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (JwtException e) {
            return null;
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            Date expiration = jwtParser
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration();
            return expiration.before(new Date());
        } catch (JwtException e) {
            return true;
        }
    }
}
