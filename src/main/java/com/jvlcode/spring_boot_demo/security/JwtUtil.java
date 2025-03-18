package com.jvlcode.spring_boot_demo.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private static final String SECRET_KEY_STRING = "9W7cVK3B59dOk0SdPyqgEGrv7EUz7WhA";
	
	private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());
	
	public String generateToken(UserDetails userDetails) {
		return Jwts.builder()
				.subject(userDetails.getUsername())
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 1000  * 60 * 60))
				.signWith(SECRET_KEY, Jwts.SIG.HS256)
				.compact()
				;		
	}
	
	public boolean validateToken(String token, UserDetails userDetails) {
		return extractUsername(token).equals(userDetails.getUsername());
	}
	
	public String extractUsername(String token) {
		return Jwts.parser()
		.verifyWith(SECRET_KEY)
		.build()
		.parseSignedClaims(token)
		.getPayload()
		.getSubject();
	}
}
