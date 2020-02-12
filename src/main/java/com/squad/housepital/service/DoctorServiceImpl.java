package com.squad.housepital.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.squad.housepital.constant.Constant;
import com.squad.housepital.dto.AppointmentRequestDto;
import com.squad.housepital.dto.ResponseDto;
import com.squad.housepital.entity.Doctor;
import com.squad.housepital.entity.DoctorSlot;
import com.squad.housepital.entity.Hospital;
import com.squad.housepital.repository.DoctorRepository;
import com.squad.housepital.repository.DoctorSlotRepository;
import com.squad.housepital.repository.HospitalRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DoctorServiceImpl implements DoctorService {

	@Autowired
	DoctorSlotRepository doctorSlotRepository;
	
	@Autowired
	DoctorRepository doctorRepository;
	
	@Autowired
	HospitalRepository hospitalRepository;
	
	public ResponseDto addAppointmentSlot(AppointmentRequestDto appointmentRequestDto) {
		log.info("Entering into addAppointmentSlot of DoctorServiceImpl");
		
		Optional<Doctor> doctorResponse=doctorRepository.findById(appointmentRequestDto.getDoctorId());
		if(!doctorResponse.isPresent()) {
			
		}
		Optional<Hospital> hospitalResponse=hospitalRepository.findById(appointmentRequestDto.getHospitalId());
		if(!hospitalResponse.isPresent()) {
			
		}
		DoctorSlot doctorSlot= new DoctorSlot();
		doctorSlot.setHospital(hospitalResponse.get());
		doctorSlot.setDoctor(doctorResponse.get());
		BeanUtils.copyProperties(appointmentRequestDto, doctorSlot);
		int slotSize=LocalTime.parse(""+appointmentRequestDto.getSlotToTime()).getHour()-LocalTime.parse(""+appointmentRequestDto.getFromTime()).getHour()/Constant.SLOT_INTERVAL;
		
		return new ResponseDto();
		
	}
}
