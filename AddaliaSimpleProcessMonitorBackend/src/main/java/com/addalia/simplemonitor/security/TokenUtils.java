package com.addalia.simplemonitor.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class TokenUtils {

	// Esto podría meterse en el application.properties
	private final static String ACCESS_TOKEN_SECRET = "EnElPaisDeLosCiegosElTuertoEsElPutoAmo";
	private final static Long ACCESS_TOKEN_VALIDITY_SECONDS = 2_592_000L; // 30dias

	public static String createToken(UserDetailsImpl userDetails, Authentication authentication) {

		long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1000; // a ms
		long currentTime = System.currentTimeMillis();

		Date issuedDate = new Date(currentTime);
		Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

		// Claims
		Map<String, Object> extra = new HashMap<>();
		final String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		extra.put("fullName", userDetails.getFullname());
		extra.put("roles", authorities);

		return Jwts.builder().setSubject(userDetails.getUsername()).setExpiration(expirationDate)
				.setIssuedAt(issuedDate).addClaims(extra).signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
				.compact();
	}

	public static UsernamePasswordAuthenticationToken getAuthentication(String token) {

		try {
			Claims claims = Jwts.parserBuilder().setSigningKey(ACCESS_TOKEN_SECRET.getBytes()).build()
					.parseClaimsJws(token).getBody();

			String username = claims.getSubject();
			String roles = claims.get("roles", String.class);

			final Collection<SimpleGrantedAuthority> authorities = Arrays.stream(roles.split(","))
					.map(SimpleGrantedAuthority::new).collect(Collectors.toList());

			return new UsernamePasswordAuthenticationToken(username, null, authorities);
		} catch (JwtException e) {
			e.printStackTrace();
			return null;
		}

	}

}
