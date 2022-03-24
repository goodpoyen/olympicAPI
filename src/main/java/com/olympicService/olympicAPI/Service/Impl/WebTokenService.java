package com.olympicService.olympicAPI.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebTokenService {
	@Autowired
	private JWTService JWTService;

	private String secret = "-555018516626007488A";

	public String getAccessToken() {
		long timeout = 1000 * 60 * 30;

		String accessToken = JWTService.generateToken(timeout, "olympic", secret);

		return accessToken;
	}

	public String getReflashToken() {
		long timeout = 1000 * 60 * 60 * 4;

		String reflashToken = JWTService.generateToken(timeout, "twolympic", secret);

		return reflashToken;
	}

	public Boolean encodeAccessToken(String token) {
		return JWTService.validateToken(token, "olympic", secret);
	}

	public Boolean encodeReflashToken(String token) {
		return JWTService.validateToken(token, "twolympic", secret);
	}
}
