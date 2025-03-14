package com.jvlcode.spring_boot_demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    // ✅ Use a manually defined secret key (Base64-encoded for security)
    private static final String SECRET_KEY_STRING = "h3bxCOJHqx4rMjBCwEnCZkB8gfutQb3h6N/Bu2b9Jn4=";
    
    // ✅ Decode and create a SecretKey from the Base64-encoded string
    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY_STRING));

    // ✅ Generate JWT Token
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername()) // Username as subject
                .claim("roles", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList())) // Store roles
                .issuedAt(new Date()) // Issue time
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Expire in 1 hour
                .signWith(SECRET_KEY, Jwts.SIG.HS256) // Sign token
                .compact();
    }

    // ✅ Extract username from token
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // ✅ Validate JWT Token
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // ✅ Check if the token is expired
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
        return expiration.before(new Date());
    }
}
