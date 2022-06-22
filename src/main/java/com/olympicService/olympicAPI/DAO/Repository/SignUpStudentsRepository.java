package com.olympicService.olympicAPI.DAO.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.olympicService.olympicAPI.DAO.Entity.SignUpStudents;

public interface SignUpStudentsRepository extends JpaRepository<SignUpStudents, Long> {
	SignUpStudents findByChineseNameAndIdCard(String chineseName, String idCard);
}
