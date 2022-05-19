package com.olympicService.olympicAPI.API;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.olympicService.olympicAPI.DAO.Entity.AdminUsers;
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

		if (bindingResult.hasErrors()) {
			result.put("code", 501);
			result.put("msg", "param_error");
			result.put("resultData", new JSONObject());
		} else {
			JSONObject checkToken = WebTokenService.encodeReflashToken(token.T, sessionID);

			if (checkToken.getBoolean("status")) {
				result.put("code", 200);
				result.put("msg", "success");
				result.put("resultData", new JSONObject());

			} else {
				if (checkToken.getBoolean("change")) {
					result.put("code", 401);
					result.put("msg", "token_change");
					
					AdminUsers AdminUsers = WebTokenService.getUser(checkToken.getString("account"));
					
					Map<String, Object> claims = new HashMap<>();
					
					claims.put("account", AdminUsers.getEmail());
					claims.put("level", AdminUsers.getLevel());
					claims.put("olympic", AdminUsers.getOlympic());
					
					JSONObject reultData = new JSONObject();
					
					claims.put("sessionID", sessionID);
					reultData.put("ret", WebTokenService.getReflashToken(claims));
					claims.remove("sessionID");
					reultData.put("act", WebTokenService.getAccessToken(claims));
					
					reultData.put("level", AdminUsers.getLevel());
					reultData.put("olympic", AdminUsers.getOlympic());
					
					result.put("resultData", reultData);
				}else {
					result.put("code", 400);
					result.put("msg", "token_fail");
					result.put("resultData", new JSONObject());
				}
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
			JSONObject checkToken = WebTokenService.encodeReflashToken(token.T, sessionID);

			if (checkToken.getBoolean("status")) {
				result.put("code", 200);
				result.put("msg", "success");
				
				Map<String, Object> claims = new HashMap<>();
				
				claims.put("account", checkToken.get("account"));
				claims.put("level", checkToken.get("level"));
				claims.put("olympic",checkToken.get("olympic"));

				JSONObject reultData = new JSONObject();
				reultData.put("act", WebTokenService.getAccessToken(claims));

				result.put("resultData", reultData);

			} else {
				result.put("code", 400);
				result.put("msg", "token_fail");
				result.put("resultData", new JSONObject());
			}
			
		}

		return result.toString();
	}
}
