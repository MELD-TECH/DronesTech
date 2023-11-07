package com.example.demo.service;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Drone;



@Repository
public interface DroneServiceRepository extends JpaRepository<Drone, UUID> {

	@Query("Select d FROM Drone d where d.medication.id = :medicationId")
	Drone findByMedicationId(@Param("medicationId") UUID medicationId);
	
	@Query("Select s FROM Drone s where s.serialNumber = :serialNumber")
	Drone finbBySerialNumber(@Param("serialNumber") int serialNumber);
	

}
