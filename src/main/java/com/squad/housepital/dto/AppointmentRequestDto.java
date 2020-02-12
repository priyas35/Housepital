package com.squad.housepital.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class AppointmentRequestDto {
	private Integer doctorId;
	private Integer hospitalId;
	private LocalDate date;
	private LocalTime fromTime;
	private LocalTime slotToTime;
}
