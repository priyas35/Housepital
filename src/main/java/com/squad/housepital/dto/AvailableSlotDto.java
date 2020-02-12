package com.squad.housepital.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AvailableSlotDto {
	
	private LocalDate date;
	private LocalTime slotTime;
	private Integer hospitalId;
	private String hospitalName;
	private Integer patientId;
	private String patientName;
	private Long mobile;
	private String email;

}
