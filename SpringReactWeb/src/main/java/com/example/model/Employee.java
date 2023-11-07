package com.example.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Employee {

	public Employee() {
		
	}
	
	/*
	 * public Employee(@JsonProperty("id") UUID id,@JsonProperty("lastname") String
	 * lastname,@JsonProperty("firstname") String firstname,@JsonProperty("email")
	 * String email,@JsonProperty("age") int age,@JsonProperty("department") String
	 * departmentid, String deptName, String deptLocation) { this.id = id;
	 * this.lastname = lastname; this.firstname = firstname; this.email = email;
	 * this.age = age;
	 * 
	 * Department dept = new Department();
	 * dept.setId(UUID.fromString(departmentid)); dept.setName(deptName);
	 * dept.setLocation(deptLocation); this.department = dept; }
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	private String lastname;
	
	private String firstname;
	
	private String email; 
	
	private int age;
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "id", nullable = true)
	private Department department;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	
}
