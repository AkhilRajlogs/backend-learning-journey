package com.security.bank.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtAuthenticationHelper {

	private static final String SECRET = "thisisacodingninjasdemonstrationforsecretkeyinspringsecurityjsonwebtokenauthentication";

	private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	private SecretKeySpec getSigningKey() {

		return new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS512.getJcaName());
	}

	public String getUsernameFromToken(String token) {

		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) {

		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {

		Claims claims = getAllClaimsFromToken(token);

		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {

		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {

		Date expiration = getExpirationDateFromToken(token);

		return expiration.before(new Date());
	}

	public String generateToken(UserDetails userDetails) {

		Map<String, Object> claims = new HashMap<>();

		return doGenerateToken(claims, userDetails.getUsername());
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(getSigningKey(), SignatureAlgorithm.HS512).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {

		String username = getUsernameFromToken(token);

		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}
}