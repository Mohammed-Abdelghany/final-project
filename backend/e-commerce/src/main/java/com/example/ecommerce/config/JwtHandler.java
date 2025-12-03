package com.example.ecommerce.config;

import com.example.ecommerce.helper.JwtToken;
import com.example.ecommerce.model.User;
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
    @Autowired
    public JwtHandler(JwtToken jwtToken) {
        this.jwtToken=jwtToken;
        Key key= Keys.hmacShaKeyFor(jwtToken.getSecret_Key().getBytes(StandardCharsets.UTF_8));
        this.jwtBuilder= Jwts.builder().signWith(key);
        this.jwtParser= Jwts.parserBuilder().setSigningKey(key).build();
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

    public String validateToken(String token) {
     if (!jwtParser.isSigned(token)) {
         throw new RuntimeException("Invalid token");
     }
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
     String username = claims.getSubject();
    Date issued =  claims.getIssuedAt();
     Date expiration = claims.getExpiration();
     Date now = new Date();
         if (now.after(expiration)) {
             throw new RuntimeException("Token expired");
         }
     return username;

    }

}
