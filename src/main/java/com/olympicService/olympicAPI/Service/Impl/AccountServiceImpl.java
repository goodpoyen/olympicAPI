package com.olympicService.olympicAPI.Service.Impl;

import java.util.List;

import javax.security.auth.message.AuthException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olympicService.olympicAPI.DAO.Entity.AdminUsers;
import com.olympicService.olympicAPI.DAO.Repository.AdminUsersRepository;

@Service
public class AccountServiceImpl {
	@Autowired
	private AES256ServiceImpl AES256ServiceImpl;

	@Autowired
	private AdminUsersRepository AdminUsersRepository;

	public List<AdminUsers> getAccountList() throws AuthException {
		AES256ServiceImpl.setKey("uBdUx82vPHkDKb284d7NkjFoNcKWBuka", "c558Gq0YQK2QUlMc");

		List<AdminUsers> AdminUsersList = AdminUsersRepository.findAll();
		for (AdminUsers data : AdminUsersList) {

			data.setEmail(AES256ServiceImpl.decode(data.getEmail().toString()));
		}

		return AdminUsersList;
	}

}
