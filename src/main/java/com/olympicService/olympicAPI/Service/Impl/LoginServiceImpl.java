package com.olympicService.olympicAPI.Service.Impl;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olympicService.olympicAPI.DAO.Entity.AdminUsers;
import com.olympicService.olympicAPI.DAO.Repository.AdminUsersRepository;
import com.olympicService.olympicAPI.Service.LoginService;
import com.olympicService.olympicAPI.Service.utils.Tool;

@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private AES256ServiceImpl AES256ServiceImpl;

	@Autowired
	private WebTokenServiceImpl WebTokenService;

	@Autowired
	private Tool Tool;

	@Autowired
	private AdminUsersRepository AdminUsersRepository;

	private Logger logger = LoggerFactory.getLogger("loginLog");

	public JSONObject checkLogin(String account, String password, String SessionID, HttpServletRequest request) throws NoSuchAlgorithmException {
		JSONObject user = new JSONObject();
		JSONObject log = new JSONObject();
		String status = "";

		log.put("action", "login");
		log.put("account", account);
		log.put("SessionID", SessionID);

		AES256ServiceImpl.setKey("uBdUx82vPHkDKb284d7NkjFoNcKWBuka", "c558Gq0YQK2QUlMc");

		account = AES256ServiceImpl.encode(account);

		password = Tool.getMD5(password);

		AdminUsers AdminUsers = AdminUsersRepository.findByEmailAndPassword(account, password);
		
//		System.out.println(AdminUsers);

		Map<String, Object> claims = new HashMap<>();

		if (AdminUsers != null) {
			claims.put("account", account);
			claims.put("level", AdminUsers.getLevel());
			claims.put("olympic", AdminUsers.getOlympic());
			
			String ip =Tool.getIpAddr(request);
			ip =Tool.fakeIp(ip);
			claims.put("ip", ip);

			user.put("status", true);
			claims.put("sessionID", SessionID);
			user.put("ret", WebTokenService.getReflashToken(claims));
			claims.remove("sessionID");
			user.put("act", WebTokenService.getAccessToken(claims));
			
			user.put("level", AdminUsers.getLevel());
			user.put("olympic", AdminUsers.getOlympic());

			status = "Success";
		} else {
			user.put("status", false);
			status = "Fail";
		}

		log.put("status", status);

		logger.info(log.toString());

		return user;
	}
	
	public JSONObject getuser(String account) {
		JSONObject user = new JSONObject();
		
		AdminUsers AdminUsers = AdminUsersRepository.findByEmail(account);
		
		user.put("olympic", AdminUsers.getOlympic());
		user.put("level", AdminUsers.getLevel());
		
		return user;
	}
}
