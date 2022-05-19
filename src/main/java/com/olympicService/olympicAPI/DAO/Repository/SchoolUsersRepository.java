package com.olympicService.olympicAPI.DAO.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.olympicService.olympicAPI.DAO.Entity.SchoolUsers;

public interface SchoolUsersRepository extends JpaRepository<SchoolUsers, Long> {
	@Query(value = "select a.*, b.school_name from school_users as a left join school_list as b on a.school_number = b.school_number", nativeQuery = true)
	List<Map<String, Object>> getSchoolUsers();
	
	@Query(value = "select a.*, b.school_name from school_users as a left join school_list as b on a.school_number = b.school_number where olympic like %?1%", nativeQuery = true)
	List<Map<String, Object>> getSchoolUsersForOmlypic(String omlypic);
}
