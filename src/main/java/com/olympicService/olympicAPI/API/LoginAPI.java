package com.olympicService.olympicAPI.API;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.olympicService.olympicAPI.Service.Impl.LoginServiceImpl;
import com.olympicService.olympicAPI.valid.LoginValid;

@RestController
public class LoginAPI {
	@Autowired
	private LoginServiceImpl LoginServiceImpl;

	@PostMapping("/login")
	public String loginAPI(@Valid @RequestBody LoginValid user, BindingResult bindingResult)
			throws NoSuchAlgorithmException, JSONException {
		JSONObject result = new JSONObject();

		if (bindingResult.hasErrors()) {
			result.put("code", 501);
			result.put("msg", "param_error");
			result.put("resultData", new JSONObject());
		} else {
			JSONObject info = LoginServiceImpl.checkLogin(user.getAccount(), user.getPassword());

			if (info.getBoolean("status")) {
				result.put("code", 200);
				result.put("msg", "success");

				JSONObject reultData = new JSONObject();

				reultData.put("id", info.get("id"));
				reultData.put("level", info.get("level"));
				reultData.put("ret", info.get("ret"));
				reultData.put("act", info.get("act"));

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
