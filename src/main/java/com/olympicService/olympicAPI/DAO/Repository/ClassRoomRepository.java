package com.olympicService.olympicAPI.DAO.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.olympicService.olympicAPI.DAO.Entity.ClassRoom;

public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {
	@Query(value = "select a.code_name, a.area_name, SUM((b.row_number * b.column_number) + b. default_number) as seat_total \n"
			+ "from exam_area as a\n" + "left join class_room as b on a.area_id = b.area_id\n"
			+ "group by a.code_name, a.area_name", nativeQuery = true)
	List<Map<String, Object>> getClassSeatTotal();
}
