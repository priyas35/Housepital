package com.squad.housepital.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class SlotDto {
	
	private LocalDate date;
	private LocalTime slotTime;
	private Integer hospitalId;
	private String hospitalName;
	private Integer doctorSlotId;

}
