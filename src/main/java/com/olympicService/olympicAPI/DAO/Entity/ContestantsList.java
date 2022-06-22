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
@Table(name = "contestants_list")
@Data
@NoArgsConstructor
public class ContestantsList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contestants_id")
	public Integer contestantsId;
	
	@Column(name = "oly_id")
	public Integer olyId;
	
	@Column(name = "st_id")
	public Integer stId;
	
	@Column(name = "area_id")
	public Integer areaId;
	
	@Column(name = "exam_code")
	public String examCode;
	
	@Column(name = "score")
	public String score;
}
