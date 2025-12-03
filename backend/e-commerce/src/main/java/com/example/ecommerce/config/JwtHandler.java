package com.example.ecommerce.config;

import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.helper.JwtToken;
import com.example.ecommerce.mapper.UserMapper;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repo.UserRepo;
import com.example.ecommerce.service.AuthService;
import com.example.ecommerce.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtHandler {
    private final JwtBuilder jwtBuilder;
    private final JwtParser jwtParser;
    private final JwtToken jwtToken;
    private final UserRepo userRepo;
    private final UserMapper userMapper;

    @Autowired
    public JwtHandler(JwtToken jwtToken, UserRepo userRepo, UserMapper userMapper) {
        this.jwtToken=jwtToken;
        Key key= Keys.hmacShaKeyFor(jwtToken.getSecret_Key().getBytes(StandardCharsets.UTF_8));
        this.jwtBuilder= Jwts.builder().signWith(key);
        this.jwtParser= Jwts.parserBuilder().setSigningKey(key).build();
        this.userRepo = userRepo;
        this.userMapper = userMapper;
    }

    public String generateToken(User user) {
        Date now = new Date();
        Date ExpiryDate = Date.from(now.toInstant().plus(jwtToken.getDuration()));
    return jwtBuilder
        .setSubject(user.getUsername()
        ).setIssuedAt(now
        ).setExpiration(ExpiryDate
        ).claim("roles",user.getRoles())
            .compact();

    };
    public UserDto  validateToken(String token) {
     if (!jwtParser.isSigned(token)) {
         throw new RuntimeException("Invalid token");
     }
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        String username = claims.getSubject();
        Date issued =  claims.getIssuedAt();
        Date expiration = claims.getExpiration();
        Date now = new Date();
         if (now.after(expiration)) {
             throw new RuntimeException("token.expired");
         }
        UserDto userDto =userMapper.userToUserDto(userRepo.findByUsername(username));
         if (userDto.getUsername() == null) {
                throw new RuntimeException("token.invalid");
         }
        boolean tokenValid = issued.before(new Date()) && expiration.after(new Date());
        if (tokenValid) {
            return userDto;
        } else {
            throw new RuntimeException("token.invalid");
        }

    }

}
