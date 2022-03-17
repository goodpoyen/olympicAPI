package com.olympicService.olympicAPI.DAO.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.olympicService.olympicAPI.DAO.Entity.AdminUsers;

public interface AdminUsersRepository extends JpaRepository<AdminUsers, Long>{
	
	AdminUsers findByEmailAndPassword(String email, String password);
}
