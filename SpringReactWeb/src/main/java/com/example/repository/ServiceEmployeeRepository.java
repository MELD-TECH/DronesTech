package com.example.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Employee;

public interface ServiceEmployeeRepository extends JpaRepository<Employee, UUID> {

}
