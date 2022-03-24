package com.olympicService.olympicAPI.Service;

import java.security.NoSuchAlgorithmException;

import org.json.JSONObject;

public interface LoginService {

	public JSONObject checkLogin(String account, String password) throws NoSuchAlgorithmException;

}
