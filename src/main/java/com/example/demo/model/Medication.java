/**
 * 
 */
package com.example.demo.model;

import java.util.UUID;

import javax.validation.constraints.Size;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Builder;

/**
 * These are the medication model that will be transported by the drones
 */

@Builder
@Entity
@Table(name = "medication")
public class Medication {
	

	public Medication() {}
	
	public Medication(UUID id, String name, int weight, String code, byte[] image, DrugMeasurement measurement) {
		this.id = id;
		this.name = name;
		this.weight = weight;
		this.code = code;
		this.image = image;
		this.measurement = measurement;
	}
	
	public Medication(UUID id, String name, int weight, String code, DrugMeasurement measurement) {
		this.id = id;
		this.name = name;
		this.weight = weight;
		this.code = code;	
		this.measurement = measurement;
	}

	public Medication(UUID id) {
		this.id = id;
	}
	
	@Id
	@Column(length = 100)
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Size(message = "To register drug name, use only (letters, numbers, '-', '_') ")
	@Column(unique = true)
	private String name; 
	
	private int weight;
	
	@Size(message = "To register drug code, use only upper case letters, underscore and numbers")
	private String code; 
	
	@Lob
	@Column(name = "drug_image", nullable = true)
	private byte[] image; 

	@Column(name = "drugmeasurement")
	private DrugMeasurement measurement;
	
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the image
	 */
	public byte[] getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}

	public DrugMeasurement getMeasurement() {
		return measurement;
	}

	public void setMeasurement(DrugMeasurement measurement) {
		this.measurement = measurement;
	}


	
	
	
}

