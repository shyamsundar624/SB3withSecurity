package com.ims.security;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.extensions.compactnotation.CompactConstructor;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtUtils {
//expiration in 6 month
	private static final long EXPIRATION_TIME_IN_MILLISEC = 100l * 60l * 24l * 30l * 6l;
	private SecretKey key;
	@Value("${secretJwtString}")
	private String secretJwtString;

	@PostConstruct
	public void init() {
		byte[] keybytes = secretJwtString.getBytes();
		this.key = new SecretKeySpec(keybytes, "HmacSHA256");
	}

	public String generateToken(String email) {
		return Jwts.builder().subject(email).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_IN_MILLISEC)).signWith(key).compact();
	}

	public String getUsernameFromToken(String token) {
		return extractClaims(token, Claims::getSubject);
	}

	private <T> T extractClaims(String token, Function<Claims, T> claims) {

		return claims.apply(Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload());
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String jwtToken) {

		return extractClaims(jwtToken, Claims::getExpiration).before(new Date());
	}

}
