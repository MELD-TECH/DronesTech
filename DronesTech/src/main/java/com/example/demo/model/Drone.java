package com.example.demo.model;

import java.util.UUID;

import javax.validation.constraints.Size;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Type;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;



@Builder
@Entity
@Table(name = "drone")
public class Drone {
	
	public Drone() {}
	
	public Drone(UUID id, int serialNumber, Model model, int weightLimit, int batteryCapacity, State state) {
		this.id = id;
		this.serialNumber = serialNumber;
		this.model = model;
		this.weightLimit = weightLimit;
		this.batteryCapacity = batteryCapacity;
		this.state = state;		
	}
	
	public Drone(UUID id, int serialNumber, Model model, int weightLimit, int batteryCapacity, State state, Medication medication) {
		this.id = id;
		this.serialNumber = serialNumber;
		this.model = model;
		this.weightLimit = weightLimit;
		this.batteryCapacity = batteryCapacity;
		this.state = state;
		this.medication = medication;
	}

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 100)
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(name="serial_number", length = 100)
	private int serialNumber; // (100 characters max);
	
	@Enumerated(EnumType.STRING)
	private Model model;
	 
	@Size(min = 1, max = 500, message = "weight limit should not be more than 500 grams")
	@Column(name="weight_limit")
	private int weightLimit; // (500gr max)
	
	@Column(name="battery_capacity")
	private int batteryCapacity; //(100 max)
	
	@Enumerated(EnumType.STRING)
	private State state; 
	
	@JoinColumn(referencedColumnName = "id", name = "medication_id", nullable = true)
	@ManyToOne()	
	private Medication medication;

	/**
	 * @return the medication
	 */
	
	public Medication getMedication() {
		return medication;
	}

	/**
	 * @param medication the medication to set
	 */
	public void setMedication(Medication medication) {
		this.medication = medication;
	}

	/**
	 * @return the id
	 */
	
	public UUID getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * @return the serialNumber
	 */
	public int getSerialNumber() {
		return serialNumber;
	}

	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * @return the model
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(Model model) {
		this.model = model;
	}


	/**
	 * @return the weightLimit
	 */
	public int getWeightLimit() {
		return weightLimit;
	}

	/**
	 * @param weightLimit the weightLimit to set
	 */
	public void setWeightLimit(int weightLimit) {
		this.weightLimit = weightLimit;
	}

	/**
	 * @return the batteryCapacity
	 */
	public int getBatteryCapacity() {
		return batteryCapacity;
	}

	/**
	 * @param batteryCapacity the batteryCapacity to set
	 */
	public void setBatteryCapacity(int batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}

	/**
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(State state) {
		this.state = state;
	}
	
	
	
	
	
	

}

