package com.olympicService.olympicAPI.Service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.security.auth.message.AuthException;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olympicService.olympicAPI.DAO.Repository.SignUpStudentsRepository;

@Service
public class PersonnelImpl {
	@Autowired
	private SignUpStudentsRepository SignUpStudentsRepository;

	@Autowired
	private AES256ServiceImpl AES256ServiceImpl;

	public JSONArray getpersonnls(String account, Integer olyId) throws AuthException {
		AES256ServiceImpl.setKey("uBdUx82vPHkDKb284d7NkjFoNcKWBuka", "c558Gq0YQK2QUlMc");
		List<Map<String, Object>> users = new ArrayList();

		users = SignUpStudentsRepository.getpersonnels(olyId);

		JSONArray result = new JSONArray(users);
//		System.out.println(result);
		for (int subIndex = 0; subIndex < result.length(); subIndex++) {
			result.getJSONObject(subIndex).put("birthday",
					AES256ServiceImpl.decode(result.getJSONObject(subIndex).getString("birthday")));
			result.getJSONObject(subIndex).put("email",
					AES256ServiceImpl.decode(result.getJSONObject(subIndex).getString("email")));
		}

		return result;
	}
}
