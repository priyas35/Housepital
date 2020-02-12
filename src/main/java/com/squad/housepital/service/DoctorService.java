package com.squad.housepital.service;

import com.squad.housepital.dto.AppointmentRequestDto;
import com.squad.housepital.dto.ResponseDto;
import com.squad.housepital.exception.DoctorNotFoundException;
import com.squad.housepital.exception.HospitalNotFoundException;

public interface DoctorService {

	ResponseDto addAppointmentSlot(AppointmentRequestDto appointmentRequestDto)
			throws DoctorNotFoundException, HospitalNotFoundException;

}
