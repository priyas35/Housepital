package com.squad.housepital.service;

import java.util.List;

import com.squad.housepital.dto.AvailableSlotDto;
import com.squad.housepital.dto.DoctorDto;
import com.squad.housepital.dto.LoginRequestDto;
import com.squad.housepital.dto.LoginResponseDto;
import com.squad.housepital.dto.SlotDto;
import com.squad.housepital.exception.DoctorNotFoundException;
import com.squad.housepital.exception.SlotNotFoundException;

public interface DoctorService {

	LoginResponseDto authenticateDoctor(LoginRequestDto loginRequestDto) throws DoctorNotFoundException;

	DoctorDto getDoctorDetails(Integer doctorId) throws DoctorNotFoundException;

	List<SlotDto> getSlotsForPatient(Integer doctorId) throws DoctorNotFoundException, SlotNotFoundException;

	List<AvailableSlotDto> getSlotsForDoctor(Integer doctorId) throws DoctorNotFoundException, SlotNotFoundException;

}
