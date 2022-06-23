package com.olympicService.olympicAPI.Service;

import java.util.Map;

import org.json.JSONObject;

public interface ExamCodeService {

	public JSONObject checkSeatTotal();
	
	public JSONObject setExamCode();
	
	public void EditExamRule(Map<String, Object> examCodeData, Map<String, Object> Students);
	
	public void saveData(String type);
}
