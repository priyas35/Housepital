package com.squad.housepital.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorDto {
	
	private String doctorName;
	private Integer experience;
	private String email;
	private Double rating;
	private String specialization;
	private String imageUrl;
	private Long mobile;
	private Double consultationFee;
	private String password;

}
