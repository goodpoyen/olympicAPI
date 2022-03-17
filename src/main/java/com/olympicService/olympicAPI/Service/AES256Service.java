package com.olympicService.olympicAPI.Service;

public interface AES256Service {
	String encode(String content);
	
	String decode(String content);
}
