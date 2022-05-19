package com.olympicService.olympicAPI.Service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.security.auth.message.AuthException;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olympicService.olympicAPI.DAO.Entity.AdminUsers;
import com.olympicService.olympicAPI.DAO.Repository.AdminUsersRepository;
import com.olympicService.olympicAPI.DAO.Repository.SchoolUsersRepository;

@Service
public class SchoolUsersServiceImpl {
	@Autowired
	private SchoolUsersRepository SchoolUsersRepository;
	
	@Autowired
	private AdminUsersRepository AdminUsersRepository;

	@Autowired
	private AES256ServiceImpl AES256ServiceImpl;

	public JSONArray getSchoolUsers(String account) throws AuthException {
		AES256ServiceImpl.setKey("uBdUx82vPHkDKb284d7NkjFoNcKWBuka", "c558Gq0YQK2QUlMc");
		
		AdminUsers AdminUsers = AdminUsersRepository.findByEmail(account);
		
		List<Map<String, Object>> users = new ArrayList();

		if (AdminUsers.getLevel().equals("1")) {
			users = SchoolUsersRepository.getSchoolUsers();
		} else {
//			System.out.println(AdminUsers.getOlympic());
			users = SchoolUsersRepository.getSchoolUsersForOmlypic(AdminUsers.getOlympic());
		}

		JSONArray result = new JSONArray(users);
//		System.out.println(result);
		for (int subIndex = 0; subIndex < result.length(); subIndex++) {
			result.getJSONObject(subIndex).put("tel",
					AES256ServiceImpl.decode(result.getJSONObject(subIndex).getString("tel")));
			result.getJSONObject(subIndex).put("email",
					AES256ServiceImpl.decode(result.getJSONObject(subIndex).getString("email")));
		}

		return result;
	}
}
