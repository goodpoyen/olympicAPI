package com.olympicService.olympicAPI.DAO.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "class_room")
@Data
@NoArgsConstructor
public class ClassRoom {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_id")
	public Integer roomId;

	@Column(name = "area_id")
	public Integer areaId;

	@Column(name = "code")
	public String code;

	@Column(name = "room_name")
	public String roomName;

	@Column(name = "row_number")
	public Integer rowNumber;

	@Column(name = "column_number")
	public Integer columnNumber;

	@Column(name = "default_number")
	public String defaultNumber;
}
