package com.squad.housepital.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squad.housepital.dto.BookSlotRequestDto;
import com.squad.housepital.dto.ResponseDto;
import com.squad.housepital.entity.DoctorSlot;
import com.squad.housepital.entity.Patient;
import com.squad.housepital.exception.SlotNotFoundException;
import com.squad.housepital.repository.DoctorSlotRepository;
import com.squad.housepital.repository.PatientRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PatientServiceImpl implements PatientService {
	
	@Autowired
	PatientRepository patientRepository;
	
	@Autowired
	DoctorSlotRepository doctorSlotRepository;
	
	/**
	 * 
	 * @author Raghu.
	 * 
	 *         This method will book the slot for patient and it will save the patient detail;
	 * 
	 * @since 2020-02-05
	 * @param patient name, email , mobile and slotId will be the input.
	 * @ @return status of saving.
	 * 
	 */
	@Override
	public ResponseDto bookSlot(BookSlotRequestDto bookSlotRequestDto) throws SlotNotFoundException {
		log.info("PatientServiceImpl bookSlot ---> saving patient");
		Patient patient = new Patient();
		BeanUtils.copyProperties(bookSlotRequestDto, patient);
		patientRepository.save(patient);
		log.info("PatientServiceImpl bookSlot ---> patient saved");
		
		Optional<DoctorSlot> doctorSlot = doctorSlotRepository.findById(bookSlotRequestDto.getDoctorSlotId());
		if(!doctorSlot.isPresent()) {
			throw new SlotNotFoundException("slot not found");
		}
		log.info("PatientServiceImpl bookSlot ---> booking slot");
		doctorSlot.get().setAvailability("no");
		doctorSlotRepository.save(doctorSlot.get());
		log.info("PatientServiceImpl bookSlot ---> slot booked");
		return new ResponseDto();
	}

}
