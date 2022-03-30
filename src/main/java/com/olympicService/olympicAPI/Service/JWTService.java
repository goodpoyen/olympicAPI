package com.olympicService.olympicAPI.Service;

import java.util.Map;

import javax.security.auth.message.AuthException;

import io.jsonwebtoken.Claims;

public interface JWTService {
	String generateToken(Long EXPIRATION_TIME, String secret, Map<String, Object> claims);
	
	Claims validateToken(String token, String secret) throws AuthException;

}
