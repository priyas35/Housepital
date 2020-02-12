package com.squad.housepital.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Doctor {
	
	@Id
	private Integer doctorId;
	private String doctorName;
	private Integer experience;
	private String email;
	private Double rating;
	private String specialization;
	private String certificate;
	private String imageUrl;
	private Long mobile;
	private Double consultationFee;
	private String password;
	

}
