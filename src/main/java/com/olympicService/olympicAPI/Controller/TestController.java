package com.olympicService.olympicAPI.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.olympicService.olympicAPI.Service.Impl.AES256ServiceImpl;
import com.olympicService.olympicAPI.Service.Impl.JWTService;

@RestController
public class TestController {
	@Autowired
	private AES256ServiceImpl AES256ServiceImpl;

	@Autowired
	private JWTService JWTService;

	private String secret = "-555018516626007488A";

	@GetMapping("/test")
	public String test() throws Exception {
//		long timeout = 1000 * 60 * 30;
//		
//		String a = JWTService.generateToken(timeout, "olympic", secret);
//		
//		System.out.println(a);
//		System.out.println(JWTService.validateToken(a, "olympic", secret));

		return "OK";
	}
}
