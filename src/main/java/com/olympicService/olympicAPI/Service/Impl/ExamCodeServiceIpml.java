package com.olympicService.olympicAPI.Service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olympicService.olympicAPI.DAO.Entity.ContestantsList;
import com.olympicService.olympicAPI.DAO.Repository.ClassRoomRepository;
import com.olympicService.olympicAPI.DAO.Repository.ContestantsListRepository;
import com.olympicService.olympicAPI.DAO.Repository.OlympicScheduleRepository;
import com.olympicService.olympicAPI.DAO.Repository.SignUpStudentsRepository;
import com.olympicService.olympicAPI.Service.ExamCodeService;

@Service
@Transactional
public class ExamCodeServiceIpml implements ExamCodeService {
	@Autowired
	private ClassRoomRepository ClassRoomRepository;

	@Autowired
	private SignUpStudentsRepository SignUpStudentsRepository;

	@Autowired
	private OlympicScheduleRepository OlympicScheduleRepository;

	@Autowired
	private ContestantsListRepository ContestantsListRepository;

	private List<ContestantsList> Contestants = new ArrayList<>();

	private int count = 0;

	private int BATCH_SIZE = 500;

	public JSONObject test() {
		JSONObject result = new JSONObject();

		result = checkSeatTotal();

		if (!result.getBoolean("status")) {
			return result;
		}

		setExamCode();

		return result;
	}

	public JSONObject checkSeatTotal() {
		List<Map<String, Object>> seatTotalList = ClassRoomRepository.getClassSeatTotal();

		List<Map<String, Object>> signUpTotalByArea = SignUpStudentsRepository.getSignUpTotalByArea(4);

		JSONObject result = new JSONObject();

		Boolean status = true;

		String seatError = "";

		for (Map<String, Object> seatTotal : seatTotalList) {
			for (Map<String, Object> signUpTotal : signUpTotalByArea) {
				if (seatTotal.get("code_name").equals(signUpTotal.get("area"))
						&& Integer.valueOf(seatTotal.get("seat_total").toString()) < Integer
								.valueOf(signUpTotal.get("seat_total").toString())) {
					status = false;
					seatError += "考場： " + seatTotal.get("area_name").toString() + " 座位數不足<br>";
				}
			}
		}

		if (!status) {
			result.put("status", false);
			result.put("msg", seatError);
		} else {
			result.put("status", true);
			result.put("msg", "1");
		}

		return result;
	}

	public JSONObject setExamCode() {
		JSONObject result = new JSONObject();

		List<Map<String, Object>> SignUpStudents = null;

		List<Map<String, Object>> getExamList = OlympicScheduleRepository.getExamList(4);

		String areaCode = "";

		for (Map<String, Object> examCodeData : getExamList) {
			if (SignUpStudents == null && areaCode.equals(examCodeData.get("area_code").toString())) {
				continue;
			} else if (SignUpStudents == null && !areaCode.equals(examCodeData.get("area_code").toString())) {
				areaCode = examCodeData.get("area_code").toString();

				SignUpStudents = SignUpStudentsRepository
						.getSignUpStudensByArea(examCodeData.get("area_code").toString());
			} else if (SignUpStudents != null && !areaCode.equals(examCodeData.get("area_code").toString())) {
				continue;
			}

			int seatTotal = (Integer.valueOf(examCodeData.get("row_number").toString())
					* Integer.valueOf(examCodeData.get("column_number").toString()))
					+ Integer.valueOf(examCodeData.get("default_number").toString());

			for (int i = 0; i < seatTotal; i++) {
				int index = (int) (Math.random() * SignUpStudents.size());
				EditExamRule(examCodeData, SignUpStudents.get(index));
				saveData("check");
				SignUpStudents.remove(index);

				if (SignUpStudents.size() <= 0) {
					this.count = 0;
					SignUpStudents = null;
					break;
				}
			}

			this.count = 0;
		}

		saveData("save");

		return result;
	}

	public void EditExamRule(Map<String, Object> examCodeData, Map<String, Object> Students) {
		this.count++;

		JSONObject insertDataList = new JSONObject();

		String seatNumber = "";

		String year = examCodeData.get("year").toString().substring(2, 4);

		if (this.count < 10) {
			seatNumber = "00" + this.count;
		} else if (this.count >= 10 && this.count < 100) {
			seatNumber = "0" + this.count;
		}

		String ExamCode = examCodeData.get("olympic_code").toString() + year + examCodeData.get("area_code").toString()
				+ examCodeData.get("class_code").toString() + seatNumber;

		insertDataList.put("olyId", Integer.valueOf(examCodeData.get("oly_id").toString()));
		insertDataList.put("areaId", Integer.valueOf(examCodeData.get("area_id").toString()));
		insertDataList.put("stId", Integer.valueOf(Students.get("st_id").toString()));
		insertDataList.put("examCode", ExamCode);
		insertDataList.put("seatNumber", this.count);

		this.Contestants.add(new ContestantsList(insertDataList));
	}

	public void saveData(String type) {
		try {
			if (type.equals("check")) {
				if (this.Contestants.size() >= BATCH_SIZE) {
					ContestantsListRepository.saveAll((Iterable<ContestantsList>) Contestants);
					this.Contestants = new ArrayList<>();
				}
			} else if (type.equals("save")) {
				ContestantsListRepository.saveAll((Iterable<ContestantsList>) Contestants);
				this.Contestants = new ArrayList<>();
			}

		} catch (Exception e) {
			System.out.println(123);
//			return "檔案有問題";
		}
	}
}
