package com.squad.housepital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squad.housepital.entity.Hospital;
import com.squad.housepital.repository.HospitalRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HospitalServiceImpl implements HospitalService{

	@Autowired
	HospitalRepository hospitalRepository;
	
	public List<Hospital> getHospitalList() {
		log.info("Entering into getHospitalList of HospitalServiceImpl");
		return hospitalRepository.findAll();
	}	
}
