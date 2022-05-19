package com.olympicService.olympicAPI.API;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.olympicService.olympicAPI.Service.Impl.LoginServiceImpl;
import com.olympicService.olympicAPI.Service.Impl.WebTokenServiceImpl;
import com.olympicService.olympicAPI.valid.LoginValid;
import com.olympicService.olympicAPI.valid.TokenValid;

@RestController
public class LoginAPI {
	@Autowired
	private WebTokenServiceImpl WebTokenService;
	
	@Autowired
	private LoginServiceImpl LoginServiceImpl;

	@PostMapping("/login")
	public String loginAPI(@Valid @RequestBody LoginValid user, BindingResult bindingResult, HttpServletRequest request)
			throws NoSuchAlgorithmException, JSONException {
		JSONObject result = new JSONObject();

		String sessionID = request.getSession().toString();

		if (bindingResult.hasErrors()) {
			result.put("code", 501);
			result.put("msg", "param_error");
			result.put("resultData", new JSONObject());
		} else {
			JSONObject info = LoginServiceImpl.checkLogin(user.getAccount(), user.getPassword(), sessionID);

			if (info.getBoolean("status")) {
				result.put("code", 200);
				result.put("msg", "success");

				JSONObject reultData = new JSONObject();

				reultData.put("ret", info.get("ret"));
				reultData.put("act", info.get("act"));
				reultData.put("level", info.get("level"));
				reultData.put("olympic", info.get("olympic"));

				result.put("resultData", reultData);
			} else {
				result.put("code", 201);
				result.put("msg", "fail");
				result.put("resultData", new JSONObject());
			}
		}

		return result.toString();
	}
	
	@PostMapping("/login/user")
	public String getuser(@Valid @RequestBody TokenValid user, BindingResult bindingResult)
			throws NoSuchAlgorithmException, JSONException {
		JSONObject result = new JSONObject();

		if (bindingResult.hasErrors()) {
			result.put("code", 501);
			result.put("msg", "param_error");
			result.put("resultData", new JSONObject());
		} else {
			JSONObject checkToken = WebTokenService.encodeAccessToken(user.getT());
//			System.out.println(checkToken);
			if (checkToken.getBoolean("status")) {
				JSONObject userData = LoginServiceImpl.getuser(checkToken.getString("account"));
//				System.out.println(userData);
				result.put("code", 200);
				result.put("msg", "success");

				JSONObject reultData = new JSONObject();

				reultData.put("olympic", userData.get("olympic"));
				reultData.put("level", userData.get("level"));

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
