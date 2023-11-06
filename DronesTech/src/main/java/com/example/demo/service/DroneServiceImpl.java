package com.example.demo.service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Drone;
import com.example.demo.model.Medication;
import com.example.demo.model.State;


@Service
public class DroneServiceImpl {

	@Autowired
	DroneServiceRepository droneServiceRepository;
	
	@Autowired
	MedicationServiceRepository medService;
	
	Logger log = LoggerFactory.getLogger(DroneServiceImpl.class);

	public Drone registerDrones(Drone drone) {
		
		UUID uuid = UUID.randomUUID();
		
		drone.setId(uuid);
		
		log.info("Checking the battery capacity ...");		
				
		return droneServiceRepository.save(drone);		
		
	}
	
	
	public Drone loadMedicationOnDrone(UUID droneid, UUID medicationId) {
		
		log.info("Fetch a medication to be loaded on drone");
		
		Drone drone = updateDroneByMedication(droneid, medicationId);
		
//		log.info("Selected drone for transport is: ", drone.getSerialNumber() + " " + drone.getState());
		
		return drone;		
	}

	
	public String checkLoadedMedication(UUID medicationId) {
				
		Medication meds = medService.findById(medicationId).get();
		
		log.info("Check loaded medication details ...");
		log.info("-----------------------------------------------------");
		log.info("Medication Name: ", meds.getName());
		log.info("Medication Drug Code: ", meds.getCode());
		log.info("Medication weight capacity: ", meds.getWeight());
		log.info("Medication label image: ", meds.getImage());
		log.info("------------------------------------------------------");
		
		
		String medpack = "Drug Name: " + meds.getName() + " " + "Drug Code " + meds.getCode();
		return medpack;
		
	}
	
	public Collection<Drone> checkAvailableDrones() {
		
		log.info("Checking all available Drones ...");
		
		return droneServiceRepository.findAll();
	}
	
	public int getDroneBatteryLevel(UUID droneId) {
		Optional<Drone> opt = droneServiceRepository.findById(droneId);
		
		int batteryLevel = 0;
		
		if(opt.isPresent()) {
			Drone drone = opt.get();
			
			log.info("Checking the drone battery capacity ...");
						
			batteryLevel = drone.getBatteryCapacity();			
			
		}
		
		
		return batteryLevel;
	}
	
	public Medication createMedication(Medication medication) {
		UUID uuid = UUID.randomUUID();
		medication.setId(uuid);
		
		return medService.save(medication);

	}
	
	
	public Optional<Drone> getDroneById(UUID id) {
		
		return droneServiceRepository.findById(id);
	}
	
	public Optional<Medication> getMedicationById(UUID id){
		
		return medService.findById(id); 
	}
	

	public boolean validateMedication(Medication medication) {
		boolean valid = false;

		
		log.info("Check if drug name have only - letters, numbers, '-', '_'");		
		
		String drugName = medication.getName();
		
		String drugregex = "^[a-zA-Z0-9_-]*$";
		
		Pattern namepattern = Pattern.compile(drugregex);
		
		Matcher namematcher = namepattern.matcher(drugName);
		
		log.info("Check if drug code contains - upper case letters, underscore and numbers");
		
		String drugCode = medication.getCode();
		
		String coderegex = "^[A-Z0-9_]*$";
		
		Pattern codepattern = Pattern.compile(coderegex);
		
		Matcher codematcher = codepattern.matcher(drugCode);
		

			
			if(namematcher.matches() == true && codematcher.matches() == true) {
				
				valid = true; 
			
			}else {
								
				valid = false;
			}
		
		return valid;
	}
	
	public Drone updateDroneByMedication(UUID droneid, UUID medicationId) {
		
		Drone drone = new Drone();
		
		Optional<Drone> opt = droneServiceRepository.findById(droneid);
		
		Optional<Medication> medopt = medService.findById(medicationId);
		
		if(opt.isPresent()) {
			drone = opt.get();
			
			Medication med = medopt.get();
			
			drone.setMedication(med); 
			
			drone = droneServiceRepository.save(drone);
		}
		
		return drone;
	}
	
	public Collection<Medication> getAllMedications(){
		
		log.info("Fetch all medications");
		
		return medService.findAll();
	}
	
	public Drone updateDroneState(UUID droneId, String state) {
		
		log.info("Retrieve drone to update it's state");
		
		State newState = null;
		
		switch(state.toUpperCase()) {
		case "IDLE": 
			newState = State.IDLE;
			break;
			
		case "LOADING":
			newState = State.LOADING;
			break;
			
		case "LOADED": 
			newState = State.LOADED;
			break;
			
		case "DELIVERING":
			newState = State.DELIVERING;
			break;
			
		case "DELIVERED":
			newState = State.DELIVERED;
			break;
			
		case "RETURNING":
			newState = State.RETURNING;
			break;

		}
		
		Optional<Drone> opt = droneServiceRepository.findById(droneId);
		
		Drone drone = null;
		
		if(opt.isPresent()) {
			drone = opt.get();
			
			drone.setState(newState);
			
			log.info("Updated drone state is ", drone.getState());

		}
		return drone;
	}

	public Medication setDrugLabelImage(UUID medicationId, MultipartFile file) {
		
		Medication med = medService.findById(medicationId).orElseThrow();
		
		try {
			med.setImage(file.getBytes());
			
			medService.save(med);
		}catch(Exception e) {
			e.getMessage();
		}
		
		return med;
		
	}
	
	public byte[] getDrugLableImage(UUID medicationId) {
		
		Medication med = medService.findById(medicationId).get();
		
		return med.getImage();
	}
	
	public boolean validateBatteryCapacity(Drone drone) {
		boolean valid = false;	
		
           if(drone.getBatteryCapacity() > 100) {
			
			log.info("Battery capacity exceeds 100%");
			
			valid = true;			
		}
           
           return valid;
	}
	
	public boolean validateDroneWeight(Drone drone) {
		boolean check = false;
		
		if(drone.getWeightLimit() > 500) {
			log.info("Drone weight limit exceeds 500 gram");
			
			check = true;
			
		}
		
		return check;
	}
	
	public boolean checkDroneLoadingStatus(Drone drone) {
		boolean check = false;
		
		log.info("Check the battery capacity and the Drone current state");
		
		if(drone.getBatteryCapacity() <= 25 && drone.getState() == State.LOADING) {
			check = true;
		}
		
		return check;
		
	}
	
	public Medication getMedicationImage(UUID medicationid) {
		Optional<Medication> opt = medService.findById(medicationid);
		
		Medication med = new Medication();
		if(opt.isPresent()) {
			log.info("Get the medication with drug image label");
			
			med = opt.get();
			
		}
		
		return med;
	}
}
