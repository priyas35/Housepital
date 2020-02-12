package com.squad.housepital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.squad.housepital.entity.Hospital;
import com.squad.housepital.service.HospitalService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/hospitals")
@CrossOrigin
@Slf4j
public class HospitalController {

	@Autowired
	HospitalService hospitalService;

	@GetMapping
	public ResponseEntity<List<Hospital>> getHospitals() {
		log.info("Entering into getHospitals of HospitalController");
		List<Hospital> hospitalList = hospitalService.getHospitalList();
		return new ResponseEntity<>(hospitalList, HttpStatus.OK);
	}
}
