package com.example.demo.controller;

import java.io.File;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ELKService {

	private static final Logger log = LoggerFactory.getLogger(ELKService.class);
	
	public JSONArray getFoodDetails() {
		
		JSONArray jsonarray = new JSONArray();
		
		log.info("getting food services");
		
		try {
			JSONParser jsonparser = new JSONParser();
			Object obj = jsonparser.parse(new FileReader("C:\\Users\\User\\eclipse-workspace\\SpringELKExample\\src\\main\\resources\\example.json"));
			
			
			JSONObject jsonObject = (JSONObject) obj;
			
			jsonarray = (JSONArray) jsonObject.get("data");
		}catch(Exception e) {
			log.error("Encountered error in the ELKService class");
			e.getMessage();
		}
		
		return jsonarray;
		
	}
}
