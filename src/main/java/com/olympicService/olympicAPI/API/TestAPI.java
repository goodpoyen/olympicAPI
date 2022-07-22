package com.olympicService.olympicAPI.API;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.olympicService.olympicAPI.Service.utils.Tool;

@RestController
public class TestAPI {
	
	@Autowired
	private Tool Tool;

	@PostMapping("/test")
	String newEmployee(@RequestBody String data, HttpServletRequest request ) {
		JSONObject result = new JSONObject(data);
		
		String ip =Tool.getIpAddr(request);	
		System.out.println(ip);
//		result.put("code", 400);
//		result.put("msg", "success");
//
//		JSONObject reultData = new JSONObject();
//
//		result.put("resultData", reultData);

//		System.out.println(object);
		return result.toString();
	}
}