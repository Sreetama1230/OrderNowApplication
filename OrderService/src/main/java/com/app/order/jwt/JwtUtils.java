package com.app.order.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtils {

	private final String SECRET = "5367566859703373367639792F423F452848284D6251655468576D5A71347437";

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }
    private Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public String extractUsername(String token) {

        return extractAllClaims(token).getSubject();
    }


    public Long extractUserId(String token) {

        return extractAllClaims(token)
                .get("userId", Long.class);
    }


    public List<String> extractRoles(String token) {

        return extractAllClaims(token)
                .get("roles", List.class);
    }

    // Validate token
    public boolean validateToken(String token) {

        try {
            Claims claims = extractAllClaims(token);

            return claims.getExpiration().after(new Date());

        } catch (Exception e) {
            return false;
        }
    }

}