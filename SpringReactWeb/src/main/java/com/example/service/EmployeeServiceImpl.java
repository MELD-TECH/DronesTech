package com.example.service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.example.model.Department;
import com.example.model.Employee;
import com.example.repository.DepartmentService;
import com.example.repository.ServiceEmployeeRepository;

@Service
public class EmployeeServiceImpl {
	
    @Autowired
	ServiceEmployeeRepository serviceEmployeeRepository;
    
    @Autowired
    DepartmentService deptService;
    
	public Collection<Employee> getAllEmployees(){
		
		return serviceEmployeeRepository.findAll();
	}
	
	public Employee save(Employee employee, Department dept) {

		  if(dept != null) { 
			  dept = deptService.save(dept);
			  employee.setDepartment(dept); 
			}
		 
		 
		return serviceEmployeeRepository.save(employee);
	}
	
	public Employee update(UUID employeeid, Employee employee) {
				
		Optional<Employee> opt = serviceEmployeeRepository.findById(employeeid);
		
		Optional<Department> depopt = deptService.findById(employee.getDepartment().getId());
		
		Employee emp = null;
		
		Department dept = null;
		
		if(opt.isPresent()) {
			emp = opt.get();
			
			emp.setId(employee.getId());
			emp.setLastname(employee.getLastname());
			emp.setFirstname(employee.getFirstname());
			emp.setEmail(employee.getEmail());
			emp.setAge(employee.getAge());
			
			
			if(depopt.isPresent()) {
				dept = depopt.get();
				
				dept.setName(employee.getDepartment().getName());
				dept.setLocation(employee.getDepartment().getLocation());
				
				emp.setDepartment(dept);
			}else if(depopt.isEmpty() || depopt == null) {
				dept = new Department();
				
				dept.setId(UUID.randomUUID());
				dept.setName(employee.getDepartment().getName());
				dept.setLocation(employee.getDepartment().getLocation());
				
				emp.setDepartment(dept);
				
			}
			
			emp = serviceEmployeeRepository.save(emp);			
			
		}
		
		return emp;
	}
	
	public Employee findEmployee(UUID id) {
		Optional<Employee> opt = serviceEmployeeRepository.findById(id);
		
		Employee employee = new Employee();
		
		if(opt.isPresent()) {
			employee = opt.get();
			
		}
		
		return employee;
	}
	
	public void removeEmployee(UUID id) {
		serviceEmployeeRepository.deleteById(id);
	}
	
	public Department createDepartment(Department department) {
		return deptService.save(department);
	}
	
	public Employee updateEmployeeByDepartment(String employeeId, String departmentId) {
		Optional<Employee> opt = serviceEmployeeRepository.findById(UUID.fromString(employeeId));
		
		Optional<Department> dep = deptService.findById(UUID.fromString(departmentId));
		
		Employee emp = new Employee();
		
		if(opt.isPresent()) {
			emp = opt.get();
			emp.setDepartment(dep.get());
			
			emp = serviceEmployeeRepository.save(emp);
		}
		
		return emp;
	}


	public Collection<Department> getAllDepartments(){
		return deptService.findAll();
	}
	
	public Department getDepartmentById(UUID id) {
		
		Optional<Department> opt = deptService.findById(id);
		
		Department department = null;
		
		if(opt.isPresent()) {
			department = opt.get();
						
		}
		
		return department;
	}
}
