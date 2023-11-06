package com.example.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Department;
import com.example.model.Employee;
import com.example.service.EmployeeServiceImpl;

@Controller
@EnableJpaRepositories("com.example.repository")
@RequestMapping("/api")
@CrossOrigin("http://localhost:3000")
public class EmployeeController {

	@Autowired
	EmployeeServiceImpl service;
	
	
	@GetMapping("/findall")
	public ResponseEntity<Object> retrieveAllEmployees(){
					
		return new ResponseEntity<>(service.getAllEmployees(), HttpStatus.OK);
	}
	
	@PostMapping("/save")
	public ResponseEntity<Object> saveEmployee(@RequestBody Employee employee){
		
		Department dept = employee.getDepartment();
		
		if(dept != null) {
			
			dept.setId(UUID.randomUUID());
			dept.setName(employee.getDepartment().getName());
			dept.setLocation(employee.getDepartment().getLocation());
		}

		
		employee.setId(UUID.randomUUID());
		
		Employee emp = service.save(employee, dept);
		
		return new ResponseEntity<Object>(emp, HttpStatus.OK);
	}
	
	@GetMapping("/find-record/{id}")
	public ResponseEntity<Object> findEmployee(@PathVariable  String id){
		UUID idstring = UUID.fromString(id);
		
		Employee emp = service.findEmployee(idstring);
		
		return new ResponseEntity<Object>(emp, HttpStatus.OK);
	}
	
	@DeleteMapping("/remove-record/{id}")
	public ResponseEntity<Object> deleteEmployee(@PathVariable String id){
		UUID idstring = UUID.fromString(id);
		service.removeEmployee(idstring);
		
		return new ResponseEntity<Object>("employee record removed", HttpStatus.OK);
	}
	
	@PutMapping("/update/{employeeid}")
	public ResponseEntity<Object> update(@PathVariable String employeeid, @RequestBody Employee employee){
		UUID empstring = UUID.fromString(employeeid);
		
		Employee emp = service.update(empstring, employee);
		
		return new ResponseEntity<Object>(emp, HttpStatus.OK);
	}
	
	@PostMapping("/save-department")
	public ResponseEntity<Object> createDepartment(@RequestBody Department department){
		
		department.setId(UUID.randomUUID());
		
		Department dept = service.createDepartment(department);
		
		return new ResponseEntity<Object>(dept, HttpStatus.OK);
	}
	
	@PutMapping("/update-department/{employeeId}/{departmentId}")
	public ResponseEntity<Object> updateEmployeeByDepartment(@PathVariable String employeeId, @PathVariable String departmentId){
		Employee emp = service.updateEmployeeByDepartment(employeeId, departmentId);
		
		return new ResponseEntity<Object>(emp, HttpStatus.OK);
	}
	
	@GetMapping("/fetch-departments")
	public ResponseEntity<Object> getAllDepartment(){
		
		return new ResponseEntity<Object>(service.getAllDepartments(), HttpStatus.OK);
	}
	
	@GetMapping("/search-department/{id}")
	public ResponseEntity<Object> findDepartmentById(@PathVariable String id){
		UUID deptid = UUID.fromString(id);
		
		Department dept = service.getDepartmentById(deptid);
		
		return new ResponseEntity<Object>(dept, HttpStatus.OK);
	}
}
