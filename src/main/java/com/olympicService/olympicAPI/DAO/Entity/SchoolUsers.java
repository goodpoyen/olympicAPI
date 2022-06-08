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
@Table(name = "school_users")
@Data
@NoArgsConstructor
public class SchoolUsers {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "u_id")
	public Integer uId;

	@Column(name = "olympic")
	public String olympic;

	@Column(name = "status")
	public String status;

	@Column(name = "school_number")
	public String schoolNumber;

	@Column(name = "name")
	public String name;

	@Column(name = "email")
	public String email;

	@Column(name = "tel")
	public String tel;

	@Column(name = "creater")
	public String creater;

	@Column(name = "createday")
	public String createday;

	@Column(name = "modifyday")
	public String modifyday;
}