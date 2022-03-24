package com.olympicService.olympicAPI.Service.Impl;

import java.time.Instant;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTService {
	public String generateToken(Long EXPIRATION_TIME, String subject, String secret) {

		return Jwts.builder().setSubject(subject)
				.setExpiration(new Date(Instant.now().toEpochMilli() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public Boolean validateToken(String token, String subject, String secret) {
		Boolean status = false;

		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		;

		if (subject.equals(claims.getSubject())) {
			status = true;
		}

		return status;
	}
}
