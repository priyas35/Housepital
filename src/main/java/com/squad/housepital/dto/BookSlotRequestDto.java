package com.squad.housepital.dto;

import lombok.Data;

@Data
public class BookSlotRequestDto {
	
	private Integer doctorSlotId;
	private String patientName;
	private Long mobile;
	private String email;

}
