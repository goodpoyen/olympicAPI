package com.olympicService.olympicAPI.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.olympicService.olympicAPI.DAO.Repository.SchoolUsersRepository;
import com.olympicService.olympicAPI.Service.Impl.AES256ServiceImpl;
import com.olympicService.olympicAPI.Service.Impl.JWTServiceImpl;
import com.olympicService.olympicAPI.Service.Impl.SchoolUsersServiceImpl;

@RestController
public class TestController {
	@Autowired
	private AES256ServiceImpl AES256ServiceImpl;

	@Autowired
	private JWTServiceImpl JWTService;

	private String secret = "-555018516626007488A";
	
	@Autowired
	private SchoolUsersRepository SchoolUsersRepository;
	
	@Autowired
	private SchoolUsersServiceImpl SchoolUsersServiceImpl;

	@GetMapping("/test")
	public String test() throws Exception {
//		AES256ServiceImpl.setKey("uBdUx82vPHkDKb284d7NkjFoNcKWBuka", "c558Gq0YQK2QUlMc");
//		String a = AES256ServiceImpl.encode("(02)22987456");
//		System.out.println(a);
//		long timeout = 1000 * 60 * 30;
//		
//		String a = JWTService.generateToken(timeout, "olympic", secret);
//		
//		System.out.println(a);
//		System.out.println(JWTService.validateToken(a, "olympic", secret));
		
//		JSONArray signupColumns = SchoolUsersServiceImpl.getSchoolUsers();
//		System.out.println(signupColumns);

		return "OK";
	}
}
