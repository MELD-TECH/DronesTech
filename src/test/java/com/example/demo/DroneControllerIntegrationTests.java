package com.example.demo;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.demo.model.Drone;
import com.example.demo.model.Medication;
import com.example.demo.model.Model;
import com.example.demo.model.State;
import com.example.demo.service.DroneServiceImpl;
import com.google.gson.Gson;

import jakarta.persistence.EntityManager;



@WebMvcTest
@ActiveProfiles("test")
public class DroneControllerIntegrationTests {
	
	@Autowired
	private MockMvc mvc;

	@MockBean
	private DroneServiceImpl service;
	
//	@Mock
//	private EntityManager manager;

	// Junit test for Get All Drones
	@Test
	public void givenDrones_whenGetDrones_thenReturnJsonArray() throws Exception{		
		
		Collection<Drone> allDrones = new ArrayList<Drone>();
		
		given(service.checkAvailableDrones()).willReturn(allDrones);
		
		// when
		ResultActions result = mvc.perform(get("/api/findall-drones")
				.contentType(MediaType.APPLICATION_JSON));
		
		// then
		result.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$.size()", is(allDrones.size())));
								
	}
	
	// Junit test to Get All Medications and return as JSON
	@Test
	public void givenMedicationObject_whenGetMedication_thenReturnJsonArray() throws Exception {
		
		// given
		Collection<Medication> medicationList = new ArrayList<>();
		
		given(service.getAllMedications()).willReturn(medicationList);
		
		// when
		ResultActions response = mvc.perform(get("/api/fetch-drugs").contentType(MediaType.APPLICATION_JSON));
		
		// then		
		response.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$.size()", is(medicationList.size())));
	}
	
	// Junit test to Create Drone Object
	@Test
	public void givenDroneObject_whenCreateDrone_thenReturnSavedDrone() throws Exception {
		
		// given
		
		Drone drone = new Drone();
		drone.setId(UUID.randomUUID());
		drone.setSerialNumber(4455293);
		drone.setBatteryCapacity(60);
		drone.setModel(Model.LIGHTWEIGHT);
		drone.setState(State.IDLE);
		drone.setWeightLimit(200);
		
		Gson droneJson = new Gson();
		String convertdrone = droneJson.toJson(drone);
		
		given(service.registerDrones(any(Drone.class))).willAnswer((target) -> target.getArgument(0));
		
		// when
		ResultActions response = mvc.perform(post("/api/register-drone")
				.content(convertdrone)
				.contentType(MediaType.APPLICATION_JSON));
		
		// then
		response
		.andExpect(status().isOk());
		
	}
	
	
	//Junit test to find Drone By Id
	@Test
	public void givenDrone_whenFindById_thenReturnDroneObject() throws Exception{
		
		UUID id = UUID.randomUUID();
		
		Drone drone = Drone.builder()
				.serialNumber(22552930)
                .batteryCapacity(60)
                .model(Model.CRUISERWEIGHT)
                .state(State.IDLE)
                .weightLimit(300)
                .build();
		

		// given
		given(service.getDroneById(id)).willReturn(Optional.of(drone));
		
		// when
		ResultActions actions = mvc.perform(get("/api/find-drone/{id}", id).contentType(MediaType.APPLICATION_JSON));

		// then
		actions.andExpect(status().isOk())

		.andDo(print())
		.andExpect(jsonPath("$.serialNumber", is(drone.getSerialNumber())))
		.andExpect(jsonPath("$.batteryCapacity", is(drone.getBatteryCapacity())))
		.andExpect(jsonPath(("$.model"), is(drone.getModel().toString())))
		.andExpect(jsonPath("$.state", is(drone.getState().toString())))
		.andExpect(jsonPath("$.weightLimit", is(drone.getWeightLimit())));
	}
}
