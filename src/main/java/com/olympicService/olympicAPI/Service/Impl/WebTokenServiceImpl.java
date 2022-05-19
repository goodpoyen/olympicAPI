package com.olympicService.olympicAPI.Service.Impl;

import java.util.Map;

import javax.security.auth.message.AuthException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olympicService.olympicAPI.DAO.Entity.AdminUsers;
import com.olympicService.olympicAPI.DAO.Repository.AdminUsersRepository;
import com.olympicService.olympicAPI.Service.WebTokenService;

import io.jsonwebtoken.Claims;

@Service
public class WebTokenServiceImpl implements WebTokenService {
	@Autowired
	private JWTServiceImpl JWTService;

	@Autowired
	private AdminUsersRepository AdminUsersRepository;

	private String secret = "-555018516626007488A";

	public String getAccessToken(Map<String, Object> claims) {
//		long timeout = 1000 * 60 * 30;
		long timeout = 1000 * 10;

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

	public JSONObject encodeAccessToken(String token) {
		JSONObject user = new JSONObject();
		user.put("status", false);
		user.put("change", false);
		user.put("account", "");
		user.put("level", "");
		user.put("olympic", "");

		try {
			Claims claims = JWTService.validateToken(token, secret);
			
			if (checkUser(claims)) {
				if (claims.get("signID").equals("olympic")) {
					user.put("status", true);
					user.put("change", false);
					user.put("account", claims.get("account"));
					user.put("level", claims.get("level"));
					user.put("olympic", claims.get("olympic"));
				}
			} else {
				user.put("status", false);
				user.put("change", true);
				user.put("account", claims.get("account"));
				user.put("level", claims.get("level"));
				user.put("olympic", claims.get("olympic"));
			}
		} catch (AuthException e) {
//	        System.out.println(156);
		}

		return user;
	}

	public JSONObject encodeReflashToken(String token, String SessionID) {
		JSONObject user = new JSONObject();
		user.put("status", false);
		user.put("change", false);
		user.put("sessionID", "");
		user.put("account", "");
		user.put("level", "");
		user.put("olympic", "");

		try {
			Claims claims = JWTService.validateToken(token, secret);

			if (checkUser(claims)) {
//				if (claims.get("signID").equals("twolympic") && claims.get("sessionID").equals(SessionID)) {
				if (claims.get("signID").equals("twolympic")) {
					user.put("status", true);
					user.put("change", false);
					user.put("sessionID", SessionID);
					user.put("account", claims.get("account"));
					user.put("level", claims.get("level"));
					user.put("olympic", claims.get("olympic"));
				}
			} else {
				user.put("status", false);
				user.put("change", true);
				user.put("sessionID", SessionID);
				user.put("account", claims.get("account"));
				user.put("level", claims.get("level"));
				user.put("olympic", claims.get("olympic"));
			}
		} catch (AuthException e) {
//	        System.out.println(e.getMessage());
		}

		return user;
	}

	public Boolean checkUser(Claims claims) {
		Boolean status = true;

		AdminUsers AdminUsers = AdminUsersRepository.findByEmail(claims.get("account").toString());

		if (!AdminUsers.getLevel().equals(claims.get("level").toString())
				|| !AdminUsers.getOlympic().equals(claims.get("olympic").toString())) {
			status = false;
		}

		return status;
	}
	
	public AdminUsers getUser(String account) {

		AdminUsers AdminUsers = AdminUsersRepository.findByEmail(account);

		return AdminUsers;
	}
}
