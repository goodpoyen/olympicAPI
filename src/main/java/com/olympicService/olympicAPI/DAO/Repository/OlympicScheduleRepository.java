package com.olympicService.olympicAPI.DAO.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.olympicService.olympicAPI.DAO.Entity.OlympicSchedule;

public interface OlympicScheduleRepository extends JpaRepository<OlympicSchedule, Long> {
	@Query(value = "select oly_id, signup_name, rules FROM olympic_schedule where signup_name = ?1 and start <= ?2 and end >= ?2", nativeQuery = true)
	List<Map<String, Object>> getOlympicSchedule(String signupName, String nowDate);

	@Query(value = "select a.oly_id, b.area_id, a.code_name as olympic_code, a.year, b.code_name as area_code, c.code_name as class_code, c.row_number, c.column_number, c.default_number\r\n"
			+ "from olympic_schedule as a \r\n" + "left join exam_area as b on a.oly_id = b.oly_id\r\n"
			+ "left join class_room as c on b.area_id = c.area_id\r\n" + "where a.oly_id = ?1\r\n"
			+ "order by area_code, class_code", nativeQuery = true)
	List<Map<String, Object>> getExamList(Integer olyId);
}
