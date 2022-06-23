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
@Table(name = "exam_area")
@Data
@NoArgsConstructor
public class ExamArea {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "area_id")
	public Integer areaId;
	
	@Column(name = "oly_id")
	public Integer olyId;
	
	@Column(name = "area_name")
	public String areaName;
	
	@Column(name = "address")
	public String address;
	
	@Column(name = "code_name")
	public String codeName;

}
