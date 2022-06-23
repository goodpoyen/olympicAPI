package com.olympicService.olympicAPI.DAO.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

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

	@Column(name = "seat_number")
	public Integer seatNumber;

	@Column(name = "score")
	public String score;

	public ContestantsList(JSONObject contestants) {
		this.contestantsId = 0;
		this.olyId = 0;
		this.stId = 0;
		this.areaId = 0;
		this.examCode = "";
		this.seatNumber = 0;
		this.score = "";

		if (!contestants.isNull("contestantsId")) {
			this.contestantsId = contestants.getInt("contestantsId");
		}

		if (!contestants.isNull("olyId")) {
			this.olyId = contestants.getInt("olyId");
		}

		if (!contestants.isNull("stId")) {
			this.stId = contestants.getInt("stId");
		}

		if (!contestants.isNull("areaId")) {
			this.areaId = contestants.getInt("areaId");
		}

		if (!contestants.isNull("examCode")) {
			this.examCode = contestants.getString("examCode");
		}

		if (!contestants.isNull("seatNumber")) {
			this.seatNumber = contestants.getInt("seatNumber");
		}

		if (!contestants.isNull("score")) {
			this.score = contestants.getString("score");
		}
	}
}
