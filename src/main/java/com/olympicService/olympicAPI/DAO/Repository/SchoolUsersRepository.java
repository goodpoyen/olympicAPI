package com.olympicService.olympicAPI.DAO.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.olympicService.olympicAPI.DAO.Entity.SchoolUsers;

public interface SchoolUsersRepository extends JpaRepository<SchoolUsers, Long> {
	SchoolUsers findBySchoolNumberAndNameAndEmailAndTel(String schoolNumber, String name, String email, String tel);

	@Query(value = "select a.*, b.school_name, c.olympic, c.status\n" + "from school_users as a \n"
			+ "left join school_list as b on a.school_number = b.school_number\n"
			+ "left join school_users_olympic as c on a.u_id = c.u_id", nativeQuery = true)
	List<Map<String, Object>> getSchoolUsers();

	@Query(value = "select a.*, b.school_name, c.olympic, c.status\n" + "from school_users as a \n"
			+ "left join school_list as b on a.school_number = b.school_number \n"
			+ "left join school_users_olympic as c on a.u_id = c.u_id\n" + "where c.olympic = ?1", nativeQuery = true)
	List<Map<String, Object>> getSchoolUsersForOmlypic(String omlypic);
}
