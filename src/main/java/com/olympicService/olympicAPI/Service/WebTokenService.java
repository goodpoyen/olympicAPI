package com.olympicService.olympicAPI.Service;

import java.util.Map;

import org.json.JSONObject;

public interface WebTokenService {
	String getAccessToken(Map<String, Object> claims);

	String getReflashToken(Map<String, Object> claims);

	JSONObject encodeAccessToken(String token);

	JSONObject encodeReflashToken(String token, String SessionID);

}
