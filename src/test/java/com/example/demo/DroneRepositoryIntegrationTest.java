package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.model.Drone;
import com.example.demo.model.Medication;
import com.example.demo.model.Model;
import com.example.demo.model.State;

import com.example.demo.service.DroneServiceRepository;
import com.example.demo.service.MedicationServiceRepository;




@DataJpaTest
@ActiveProfiles("test")
public class DroneRepositoryIntegrationTest {


	@Autowired
	private DroneServiceRepository serviceRepository;
	
	@Autowired
	private MedicationServiceRepository medRepository;
	
	@Autowired
	private TestEntityManager entity;
	

	@Test
	public void whenFindBySerialNumber_thenReturnDrone() {

		// given
		Drone drone = new Drone();
		drone.setSerialNumber(3425673);
		drone.setBatteryCapacity(750);
		drone.setModel(Model.CRUISERWEIGHT);
		drone.setWeightLimit(400);
		drone.setState(State.IDLE);
		
		drone = entity.persistAndFlush(drone);
		
		// when
		Drone findDrone = serviceRepository.finbBySerialNumber(3425673);
		
		// then
		assertThat(findDrone.getSerialNumber()).isEqualTo(drone.getSerialNumber());
		
	}
	
	@Test
	public void whenFindByMedication_thenLoadDroneWithMedication() {
		
		// given Medication		
		Medication medication = new Medication();
		medication.setName("Paracetamol");
		medication.setWeight(200);
		medication.setCode("PARC");
		
		medication = entity.persistAndFlush(medication);
		
		// given Drone
		Drone drone = new Drone();
		drone.setSerialNumber(4454393);
		drone.setBatteryCapacity(60);
		drone.setModel(Model.MIDDLEWEIGHT);
		drone.setWeightLimit(200);
		drone.setState(State.IDLE);
		drone.setMedication(medication);
		
		drone = entity.persistAndFlush(drone); 
		

		// when
		Medication newMedication = medRepository.findByMedicationName("Paracetamol");
		
		Drone loadDrone = serviceRepository.findByMedicationId(newMedication.getId());
		
		// then
		assertThat(loadDrone.getMedication()).isEqualTo(drone.getMedication());
	}
	
	

	
}
