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
@Table(name = "school_list")
@Data
@NoArgsConstructor
public class SchoolList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer id;

	@Column(name = "city_number")
	public String cityNumber;

	@Column(name = "city_name")
	public String cityName;

	@Column(name = "school_type")
	public String schoolType;

	@Column(name = "school_number")
	public String schoolNumber;

	@Column(name = "school_name")
	public String schoolName;

	@Column(name = "attached_training")
	public String attachedTraining;

	@Column(name = "training_school")
	public String trainingSchool;

	@Column(name = "attached_junior")
	public String attachedJunior;

	@Column(name = "area_number")
	public String areaNumber;

	@Column(name = "area_name")
	public String areaName;

	@Column(name = "address")
	public String address;

	@Column(name = "detail_address")
	public String detailAddress;

	@Column(name = "tel")
	public String tel;
}
