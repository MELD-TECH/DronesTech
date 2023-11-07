package com.example.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Department;

public interface DepartmentService extends JpaRepository<Department, UUID> {

}
