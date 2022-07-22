package com.olympicService.olympicAPI.Service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

public interface WebTokenService {
	String getAccessToken(Map<String, Object> claims);

	String getReflashToken(Map<String, Object> claims);

	JSONObject encodeAccessToken(String token, HttpServletRequest request);

	JSONObject encodeReflashToken(String token, String SessionID, HttpServletRequest request);

}
