package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

	@Autowired 
	StudentServiceImpl service;
	
	
	@GetMapping("/findall")
	public ResponseEntity<Object> getAllStudents(){
		
		return new ResponseEntity<Object>(service.findAllStudents(), HttpStatus.OK) ;
	}
	
	
	@PostMapping("/save")
	public ResponseEntity<Object> save(@RequestBody Student student){
		service.saveStudent(student);
		return new ResponseEntity<Object>("Records saved successfully", HttpStatus.OK);
	}
	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable String id, @RequestBody Student student){
		service.updateStudent(id);
		
		return new ResponseEntity<Object>("Student records updated", HttpStatus.OK);
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> delete(@PathVariable String id){
		service.deleteStudent(id);
		
		return new ResponseEntity<Object>("Student removed from record", HttpStatus.OK);
	}
}
