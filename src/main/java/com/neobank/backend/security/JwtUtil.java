package com.neobank.backend.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;

@Component
public class JwtUtil {
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long EXPIRATION_TIME = 1000 * 60 * 60;

    public String generateToken(Long id) {
        Date setDate = new Date();
        return Jwts.builder()
            .setSubject(String.valueOf(id))
            .setIssuedAt(setDate)
            .setExpiration(new Date(setDate.getTime() + EXPIRATION_TIME))
            .signWith(key)
            .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public String extractSubject(String token) {
        return extractAllClaims(token).getSubject();
    }
}
