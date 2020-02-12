package com.squad.housepital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.squad.housepital.dto.AppointmentRequestDto;
import com.squad.housepital.dto.ResponseDto;
import com.squad.housepital.exception.DoctorNotFoundException;
import com.squad.housepital.exception.HospitalNotFoundException;
import com.squad.housepital.service.DoctorService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/doctors")
@CrossOrigin
@Slf4j
public class DoctorController {
	
	@Autowired
	DoctorService doctorService;
	
	@PostMapping("/appointments")
	public ResponseEntity<ResponseDto> addAppointmentSlot(@RequestBody AppointmentRequestDto appointmentRequestDto) throws DoctorNotFoundException, HospitalNotFoundException{
		log.info("Entering into addAppointmentSlot of DoctorController");
		ResponseDto responseDto=doctorService.addAppointmentSlot(appointmentRequestDto);
		responseDto.setStatusCode(HttpStatus.OK.value());
		responseDto.setStatusMessage("Success");
		return new ResponseEntity<>(responseDto,HttpStatus.OK);
	}
}
