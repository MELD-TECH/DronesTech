package com.example.web;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication



public class DemoExample {

//	@Value("${spring.application.name}")
//	private String name;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SpringApplication.run(DemoExample.class, args);
		
		System.out.println("Hello World");
				
	}
	
	


}
