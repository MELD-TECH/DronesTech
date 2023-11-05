package com.example.demo;

import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;


@Service
public class StudentServiceImpl {

    @Autowired
	StudentDemoService stdService;
	

	@Cacheable("students")
	public Iterable<Student> findAllStudents() {
		
		return stdService.findAll();
	}
	
	@Cacheable("students")
	public void saveStudent(Student student) {
		stdService.save(student);
	}
	
	@Cacheable("students")
	public void updateStudent(String id) {
		Student newStudent = stdService.findById(id).get();
		
		stdService.save(newStudent);
	}
	
	@Cacheable("students")
	public void deleteStudent(String id) {
		stdService.deleteById(id);
	}
}
