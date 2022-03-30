package com.olympicService.olympicAPI.Service;

import java.util.Map;

public interface WebTokenService {
	String getAccessToken(Map<String, Object> claims);

	String getReflashToken(Map<String, Object> claims);

	Boolean encodeAccessToken(String token);

	Boolean encodeReflashToken(String token, String SessionID);

}
