package com.olympicService.olympicAPI.API;

import java.security.NoSuchAlgorithmException;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.olympicService.olympicAPI.Service.Impl.SchoolUsersServiceImpl;
import com.olympicService.olympicAPI.Service.Impl.WebTokenServiceImpl;
import com.olympicService.olympicAPI.valid.SchoolUsers.GetSchoolUsersValid;

@RestController
public class SchoolUsersAPI {
	@Autowired
	private WebTokenServiceImpl WebTokenService;

	@Autowired
	private SchoolUsersServiceImpl SchoolUsersServiceImpl;

	@PostMapping("/getSchoolUsers")
	public String getSchoolUsers(@Valid @RequestBody GetSchoolUsersValid schoolUsers, BindingResult bindingResult,
			HttpServletRequest request) throws NoSuchAlgorithmException, JSONException, AuthException {
		JSONObject result = new JSONObject();

		if (bindingResult.hasErrors()) {
			result.put("code", 501);
			result.put("msg", "param_error");
			result.put("resultData", new JSONObject());
		} else {
			JSONObject checkToken = WebTokenService.encodeAccessToken(schoolUsers.getAT());

			if (checkToken.getBoolean("status")) {
				JSONArray users = SchoolUsersServiceImpl.getSchoolUsers(checkToken.getString("account"));
				
				result.put("code", 200);
				result.put("msg", "success");
				result.put("resultData", users);

			} else {
				if (checkToken.getBoolean("change")) {
					result.put("code", 401);
					result.put("msg", "token_change");
					result.put("resultData", new JSONObject());
				}else {
					result.put("code", 400);
					result.put("msg", "token_fail");
					result.put("resultData", new JSONObject());
				}
			}
		}

		return result.toString();
	}
}
