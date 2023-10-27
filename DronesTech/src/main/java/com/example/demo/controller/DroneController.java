/**
 * 
 */
package com.example.demo.controller;


import org.springframework.http.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.context.annotation.ComponentScan;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Drone;
import com.example.demo.model.Medication;
import com.example.demo.service.DroneServiceImpl;
import com.example.demo.service.DroneServiceRepository;


/**
 * 
 */

@RestController
@RequestMapping(value = "/api")
@EnableJpaRepositories("com.example.demo.service")
@EntityScan("com.example.demo.model")
@ComponentScan(basePackages = "com.example.demo.service")
public class DroneController {

	@Autowired
	DroneServiceImpl service;
	
	@Autowired	
	DroneServiceRepository servRepository;
	
	Logger log = LoggerFactory.getLogger(DroneController.class);
	
	

	@PostMapping("/register-drone")
	public ResponseEntity<Object> registerDrone(@RequestBody Drone drone){
		
		boolean checkBattery = service.validateBatteryCapacity(drone);
		
		boolean checkWeight = service.validateDroneWeight(drone);
		
		if(checkBattery == false && checkWeight == false) {		
			
			log.info("Drone successfully registered... ");		
			
			return new ResponseEntity<>(service.registerDrones(drone), HttpStatus.OK);
		}else {
			
			log.info("Battery capacity exceeded 100%");
			log.info("Drone weight must not exceed 500 grams");
			
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("service-name", "register-drone");
			map.put("error", "Battery capacity must not exceed 100%");
			map.put("error2", "Drone Weight limit must not exceed 500 grams");
			
			return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
		}

	}
	
	@PutMapping("/load-medication/{medicationid}/{droneId}")
	public ResponseEntity<Object> loadMedications(@PathVariable UUID medicationid, @PathVariable UUID droneId){		
		
		Drone drone = servRepository.findById(droneId).get();
		
		boolean valid = service.checkDroneLoadingStatus(drone);
		
		if(valid == false) {			
			
			return new ResponseEntity<Object>(service.loadMedicationOnDrone(droneId, medicationid), HttpStatus.OK);
		}else {
			log.info("battery capacity is less than 25%; Loading state is LOADING");
			
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("service-name", "load-medication-on-drone");
			map.put("error", "Abort loading medications on Drone: the battery capacity is lower than 25");
			map.put("error2", "Loading state is currently on LOADING State ...");
			
			return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
		}
			
	}
	
	@GetMapping("/findall-drones")
	public ResponseEntity<Object> checkAvailableDrones(){
		
		return new ResponseEntity<Object>(service.checkAvailableDrones(), HttpStatus.OK);
	}
	
	@GetMapping("/battery-level/{droneId}")
	public ResponseEntity<Object> checkDroneBatteryLevel(@PathVariable UUID droneId){
		
		int level = service.getDroneBatteryLevel(droneId);
		
		if(level > 0) {
			log.info("battery level is " + String.valueOf(level));
			
			Map<String, Integer> map = new LinkedHashMap<String, Integer>();
			
			map.put("battery-capacity", service.getDroneBatteryLevel(droneId));
			
			return new ResponseEntity<Object>(map, HttpStatus.OK);
		}else {
			log.error("NO RECORD FOUND FOR DRONE BATTERY");
			
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("service-name", "battery-level");
			map.put("error", "NO RECORD FOUND");
			return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
		}

	}
	
	@PostMapping("/register-drug")
	public ResponseEntity<Object> createMedication(@RequestBody Medication medication){
			
       boolean check = service.validateMedication(medication);
		
		if(check == true) {
			log.info("New medication successfully created ...");

			return new ResponseEntity<Object>(service.createMedication(medication), HttpStatus.OK);
		}else {
			
			log.info("------------------------------------------------------------------");
			log.info("drug name must have only - letters, numbers, '-', '_'");
			log.info("drug code must contain only - upper case letters, underscore and numbers");
			log.info("------------------------------------------------------------------");

			String firstMessage = "Drug name must have only - letters, numbers, underscores, hiphen";
			String secondMessage = "Drug code must contain only - upper case letters, underscore and numbers";
			

			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("service-name", "register-drug");
			map.put("error", firstMessage);
			map.put("error2", secondMessage);
			
			return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
		}		
		
	}
	
	@GetMapping("/fetch-drugs")
	public ResponseEntity<Object> getAllMedications(){
		
		log.info("All medications are listed");
		
		return new ResponseEntity<Object>(service.getAllMedications(), HttpStatus.OK);
	}
	
	@PutMapping("/update-status/{droneid}/{status}")
	public ResponseEntity<Object> updateDroneState(@PathVariable UUID droneid, @PathVariable String status){
		
		Drone drone = service.updateDroneState(droneid, status);
		
		if(drone != null) {
			log.info("Updating drone status from controller");
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			
			map.put("id", drone.getId());
			map.put("serialNumber", drone.getSerialNumber());
			map.put("state", drone.getState());
			map.put("model", drone.getModel());
			map.put("weightLimit", drone.getWeightLimit());
			map.put("batteryCapacity", drone.getBatteryCapacity());
			
			return new ResponseEntity<Object>(map, HttpStatus.OK);
		}else {
			
			log.error("Drone state does not exist");
			
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("service-name", "update-drone-state");
			map.put("error", "Drone state does not exist");
			
			return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
		}
		
   }
	
	@PostMapping(value="/upload-image/{medicationid}/drug-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> uploadDrugImage(@PathVariable UUID medicationid, @RequestParam(value = "file", required = true) MultipartFile file){
		 
		log.info("uploading the image");
		
		return new ResponseEntity<Object>(service.setDrugLabelImage(medicationid, file), HttpStatus.OK);
	}
	
	@GetMapping("/find-drone/{id}")
	public ResponseEntity<Object> getDroneById(@PathVariable UUID id){
		
		
		Optional<Drone> opt = service.getDroneById(id);
		
		if(opt.isPresent()) {
			log.info("Retrieved drone is ... ");
			
			return new ResponseEntity<Object>(opt.get(), HttpStatus.OK);
		}else {
			log.error("Drone does not exist");
			
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("service-name", "find-drone");
			map.put("error", "Drone not found");
			
			return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
		}
		
		
	}
	

	@GetMapping("/get-image/{medicationid}")
	public ResponseEntity<Object> getMedicationImage(@PathVariable UUID medicationid){
		
		Medication medical = service.getMedicationImage(medicationid);
		
		if(medical != null) {
			byte[] img = medical.getImage();
			
			log.info("Medication image label successfully displayed...");
			
			return ResponseEntity.ok().contentType(MediaType.parseMediaType(MediaType.IMAGE_JPEG_VALUE))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"${System.currentTimeMillis()}\"")
					.body(img);
					
		}else {
			
			log.error("Medication image not found");
			
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("service-name", "get-image");
			map.put("error", "Medication image not found");
			
			return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
		}
	}
}
