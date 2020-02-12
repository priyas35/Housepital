package com.squad.housepital.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class AppointmentRequestDto {
	private Integer doctorId;
	private Integer hospitalId;
	private LocalDate date;
	private String fromTime;
	private String slotToTime;
}
