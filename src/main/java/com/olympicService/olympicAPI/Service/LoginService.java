package com.olympicService.olympicAPI.Service;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

public interface LoginService {

	public JSONObject checkLogin(String account, String password, String SessionID, HttpServletRequest request) throws NoSuchAlgorithmException;

}
