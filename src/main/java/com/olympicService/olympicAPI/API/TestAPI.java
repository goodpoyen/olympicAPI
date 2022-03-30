package com.olympicService.olympicAPI.API;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAPI {

	@PostMapping("/test")
	String newEmployee(@RequestBody String data) {
		JSONObject result = new JSONObject(data);

		result.put("code", 400);
		result.put("msg", "success");

		JSONObject reultData = new JSONObject();

		result.put("resultData", reultData);

//		System.out.println(object);
		return result.toString();
	}
}