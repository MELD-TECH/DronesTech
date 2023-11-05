package com.example.demo;


import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@RedisHash("Student")
public class Student implements Serializable{

	private static final long serialVersionUID = -5398739498821934316L;
	
	public Student() {}
	
	public Student(String id, String name, int age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}
	
	private String id;
	
	private String name;
	
	private int age;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		
		return this.getId() + " " + this.getName() + " " + this.getAge() ;
	}
}
