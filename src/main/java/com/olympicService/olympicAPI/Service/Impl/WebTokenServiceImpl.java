package com.olympicService.olympicAPI.Service.Impl;

import java.util.Map;

import javax.security.auth.message.AuthException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olympicService.olympicAPI.Service.WebTokenService;

import io.jsonwebtoken.Claims;

@Service
public class WebTokenServiceImpl implements WebTokenService {
	@Autowired
	private JWTServiceImpl JWTService;

	private String secret = "-555018516626007488A";

	public String getAccessToken(Map<String, Object> claims) {
		long timeout = 1000 * 60 * 30;

		claims.put("signID", "olympic");

		String accessToken = JWTService.generateToken(timeout, secret, claims);

		return accessToken;
	}

	public String getReflashToken(Map<String, Object> claims) {
		long timeout = 1000 * 60 * 60 * 4;

		claims.put("signID", "twolympic");

		String reflashToken = JWTService.generateToken(timeout, secret, claims);

		return reflashToken;
	}

	public Boolean encodeAccessToken(String token) {
		Boolean status = false;

		try {
			Claims claims = JWTService.validateToken(token, secret);

			if (claims.get("signID").equals("olympic")) {
				status = true;
			}
		} catch (AuthException e) {
//	        System.out.println(e.getMessage());
		}

		return status;
	}

	public Boolean encodeReflashToken(String token, String SessionID) {
		Boolean status = false;
		try {
			Claims claims = JWTService.validateToken(token, secret);
//			System.out.println("claims : " + claims.get("sessionID"));
//			System.out.println("SessionID : " + SessionID);

			if (claims.get("signID").equals("twolympic") && claims.get("sessionID").equals(SessionID)) {
				status = true;
			}
		} catch (AuthException e) {
//	        System.out.println(e.getMessage());
		}

		return status;
	}
}
