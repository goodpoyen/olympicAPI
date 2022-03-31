package com.olympicService.olympicAPI.API;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.olympicService.olympicAPI.Service.Impl.WebTokenServiceImpl;
import com.olympicService.olympicAPI.valid.TokenValid;

@RestController
public class TokenAPI {
	@Autowired
	private WebTokenServiceImpl WebTokenService;

	@PostMapping("/checkRT")
	public String checkRT(@Valid @RequestBody TokenValid token, BindingResult bindingResult,
			HttpServletRequest request) {
		JSONObject result = new JSONObject();

		String sessionID = request.getSession().toString();

		System.out.println(sessionID);

		if (bindingResult.hasErrors()) {
			result.put("code", 501);
			result.put("msg", "param_error");
			result.put("resultData", new JSONObject());
		} else {
			Boolean status = WebTokenService.encodeReflashToken(token.T, sessionID);

			if (status) {
				result.put("code", 200);
				result.put("msg", "success");

				JSONObject reultData = new JSONObject();

				result.put("resultData", reultData);

			} else {
				result.put("code", 201);
				result.put("msg", "fail");
				result.put("resultData", new JSONObject());
			}
		}

		return result.toString();
	}

	@PostMapping("/getAT")
	public String getAT(@Valid @RequestBody TokenValid token, BindingResult bindingResult, HttpServletRequest request) {
		JSONObject result = new JSONObject();

		String sessionID = request.getSession().toString();

		if (bindingResult.hasErrors()) {
			result.put("code", 501);
			result.put("msg", "param_error");
			result.put("resultData", new JSONObject());
		} else {
			Boolean status = WebTokenService.encodeReflashToken(token.T, sessionID);

			if (status) {
				result.put("code", 200);
				result.put("msg", "success");

				JSONObject reultData = new JSONObject();
				reultData.put("act", WebTokenService.getAccessToken(new HashMap<>()));

				result.put("resultData", reultData);

			} else {
				result.put("code", 201);
				result.put("msg", "fail");
				result.put("resultData", new JSONObject());
			}
		}

		return result.toString();
	}
}
