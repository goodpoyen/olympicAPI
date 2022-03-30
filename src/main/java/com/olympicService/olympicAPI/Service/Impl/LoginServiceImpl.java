package com.olympicService.olympicAPI.Service.Impl;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
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
    private  AdminUsersRepository AdminUsersRepository;

	public JSONObject checkLogin (String account, String password, String SessionID) throws NoSuchAlgorithmException {
		JSONObject user = new JSONObject();
		
		AES256ServiceImpl.setKey("uBdUx82vPHkDKb284d7NkjFoNcKWBuka", "c558Gq0YQK2QUlMc");
		
		account = AES256ServiceImpl.encode(account);

		password = Tool.getMD5(password);
		
		AdminUsers AdminUsers = AdminUsersRepository.findByEmailAndPassword(account, password);
		
		Map<String, Object> claims = new HashMap<>();
		
		claims.put("sessionID", SessionID);
		
		if (AdminUsers != null) {
			user.put("status", true);
			user.put("id", AdminUsers.getAdId());
			user.put("level", AdminUsers.getLevel());
			user.put("ret", WebTokenService.getReflashToken(claims));
			user.put("act", WebTokenService.getAccessToken(new HashMap<>()));

//			System.out.println(WebTokenService.encodeReflashToken(user.get("ret").toString(), SessionID));
//			System.out.println(WebTokenService.encodeAccessToken(user.get("act").toString()));
		} else {
			user.put("status", false);
		}

		return user;
	}
}
