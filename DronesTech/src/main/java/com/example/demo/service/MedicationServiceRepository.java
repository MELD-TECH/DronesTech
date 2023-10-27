package com.example.demo.service;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Medication;

@Repository
public interface MedicationServiceRepository extends JpaRepository<Medication, UUID> {

	@Query("FROM Medication m where m.name = :name")
	Medication findByMedicationName(@Param("name") String name);
}
