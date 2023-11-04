package com.example.demo.controller;


import java.util.Date;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rest")
public class ElasticController {

	private static final Logger log = LoggerFactory.getLogger(ElasticController.class);
	
	private final ELKService elkService;
	
	public ElasticController(ELKService service) {
		this.elkService = service;
	}
	
	@GetMapping(value="/hello")
	public String helloWorld() {
		log.info("Inside Hello World Function");
		
        String response = "Hello World! " + new Date();
        
        log.info("Response => {}",response);
        
        return response;
	}
	
	@GetMapping(value="/food-details")
	public JSONArray getFood() {
		log.info("Inside Food Detail Function");
		
        return elkService.getFoodDetails();
	}
	
}
