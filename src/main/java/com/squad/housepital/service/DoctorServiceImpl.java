package com.squad.housepital.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squad.housepital.constant.Constant;
import com.squad.housepital.dto.AppointmentRequestDto;
import com.squad.housepital.dto.ResponseDto;
import com.squad.housepital.entity.Doctor;
import com.squad.housepital.entity.DoctorSlot;
import com.squad.housepital.entity.Hospital;
import com.squad.housepital.exception.DoctorNotFoundException;
import com.squad.housepital.exception.HospitalNotFoundException;
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
	
	public ResponseDto addAppointmentSlot(AppointmentRequestDto appointmentRequestDto) throws DoctorNotFoundException, HospitalNotFoundException {
		log.info("Entering into addAppointmentSlot of DoctorServiceImpl");
		
		Optional<Doctor> doctorResponse=doctorRepository.findById(appointmentRequestDto.getDoctorId());
		if(!doctorResponse.isPresent()) {
			log.error("Exception occured in addAppointmentSlot:");
			throw new DoctorNotFoundException("Doctor Not Found");
		}
		Optional<Hospital> hospitalResponse=hospitalRepository.findById(appointmentRequestDto.getHospitalId());
		if(!hospitalResponse.isPresent()) {
			log.error("Exception occured in addAppointmentSlot:");
			throw new HospitalNotFoundException("Hospital Not Found");
		}
		List<DoctorSlot> doctorSlotList=new ArrayList<>();	
		LocalTime fromTime=LocalTime.parse(appointmentRequestDto.getFromTime());
		LocalTime toTime=LocalTime.parse(appointmentRequestDto.getSlotToTime());
		while(fromTime.isBefore(toTime)) {
			DoctorSlot doctorSlot= new DoctorSlot();
			doctorSlot.setAvailability(Constant.AVAILABLE);
			doctorSlot.setDate(appointmentRequestDto.getDate());
			doctorSlot.setDoctor(doctorResponse.get());
			doctorSlot.setHospital(hospitalResponse.get());
			doctorSlot.setSlotTime(fromTime);
			doctorSlotList.add(doctorSlot);
			fromTime=fromTime.plusMinutes(Constant.SLOT_INTERVAL);
		}
		doctorSlotRepository.saveAll(doctorSlotList);
		return new ResponseDto();	
	}
}
