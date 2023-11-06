package com.example.model;

import java.util.UUID;

import org.springframework.boot.json.GsonJsonParser;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Department {

	
	public Department() {
		
	}
	
	
	/*
	 * public Department(@JsonProperty("id") String id,@JsonProperty("name") String
	 * name,@JsonProperty("location") String location) { Department dept = new
	 * Department(); dept.setId(UUID.fromString(id)); dept.setName(name);
	 * dept.setLocation(location);
	 * 
	 * 
	 * 
	 * }
	 */
	
	
	public Department(String name, String location) {
		this.name = name;
		this.location = location;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	private String name;
	
	private String location;
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	
	
}
