package com.olympicService.olympicAPI.API;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.olympicService.olympicAPI.DAO.Entity.AdminUsers;
import com.olympicService.olympicAPI.Service.Impl.AccountServiceImpl;
import com.olympicService.olympicAPI.Service.Impl.WebTokenServiceImpl;
import com.olympicService.olympicAPI.valid.Account.CreateAccountValid;
import com.olympicService.olympicAPI.valid.Account.GetAccountListValid;

@RestController
public class AccountAPI {
	@Autowired
	private WebTokenServiceImpl WebTokenService;

	@Autowired
	private AccountServiceImpl AccountServiceImpl;

	@PostMapping("/getAccountList")
	public String getAccountList(@Valid @RequestBody GetAccountListValid accountList, BindingResult bindingResult,
			HttpServletRequest request) throws NoSuchAlgorithmException, JSONException, AuthException {
		JSONObject result = new JSONObject();

		if (bindingResult.hasErrors()) {
			result.put("code", 501);
			result.put("msg", "param_error");
			result.put("resultData", new JSONObject());
		} else {
			JSONObject checkToken = WebTokenService.encodeAccessToken(accountList.getAT(), request);

			if (checkToken.getBoolean("status")) {
				List<AdminUsers> AdminUsersList = AccountServiceImpl.getAccountList();

				result.put("code", 200);
				result.put("msg", "success");
				result.put("resultData", AdminUsersList);
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
	
	@PostMapping("/createAccount")
	public String createAccount(@Valid @RequestBody CreateAccountValid account, BindingResult bindingResult,
			HttpServletRequest request) throws NoSuchAlgorithmException, JSONException, AuthException {
		JSONObject result = new JSONObject();

		if (bindingResult.hasErrors()) {
			result.put("code", 501);
			result.put("msg", "param_error");
			result.put("resultData", new JSONObject());
		} else {
			JSONObject checkToken = WebTokenService.encodeAccessToken(account.getAT(), request);

			if (checkToken.getBoolean("status")) {
				List<AdminUsers> AdminUsersList = AccountServiceImpl.getAccountList();

				result.put("code", 200);
				result.put("msg", "success");
				result.put("resultData", AdminUsersList);
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

				return result.toString();
			}
		}

		return result.toString();
	}
}
