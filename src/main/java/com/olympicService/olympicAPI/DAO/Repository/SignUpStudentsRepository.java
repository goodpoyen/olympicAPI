package com.olympicService.olympicAPI.DAO.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.olympicService.olympicAPI.DAO.Entity.SignUpStudents;

public interface SignUpStudentsRepository extends JpaRepository<SignUpStudents, Long> {
	SignUpStudents findByChineseNameAndIdCard(String chineseName, String idCard);

	@Query(value = "select area, count(st_id) as seat_total from sign_up_students where oly_id = ?1 group by area", nativeQuery = true)
	List<Map<String, Object>> getSignUpTotalByArea(Integer olyId);

	@Query(value = "select st_id from sign_up_students where area = ?1", nativeQuery = true)
	List<Map<String, Object>> getSignUpStudensByArea(String area);
	
	@Query(value = "select a.*, b.school_name as school_name2 from sign_up_students as a\n"
			+ "left join school_list as b on a.school_name = b.school_number\n"
			+ "where oly_id = ?1", nativeQuery = true)
	List<Map<String, Object>> getpersonnels(Integer olyId);
}
