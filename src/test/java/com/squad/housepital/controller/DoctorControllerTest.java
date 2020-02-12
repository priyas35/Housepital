package com.squad.housepital.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.squad.housepital.dto.AvailableSlotDto;
import com.squad.housepital.dto.DoctorDto;
import com.squad.housepital.dto.LoginRequestDto;
import com.squad.housepital.dto.LoginResponseDto;
import com.squad.housepital.dto.SlotDto;
import com.squad.housepital.entity.Doctor;
import com.squad.housepital.exception.DoctorNotFoundException;
import com.squad.housepital.exception.SlotNotFoundException;
import com.squad.housepital.service.DoctorService;

@RunWith(MockitoJUnitRunner.class)
public class DoctorControllerTest {
	
	@InjectMocks
	DoctorController doctorController;
	
	@Mock
	DoctorService doctorService;
	
	LoginRequestDto loginRequestDto = new LoginRequestDto();
	LoginResponseDto loginResponseDto = new LoginResponseDto();
	Doctor doctor = new Doctor();
	DoctorDto doctorDto = new DoctorDto();
	SlotDto SlotDto = new SlotDto();
	List<SlotDto> availableSlots = new ArrayList<>();
	AvailableSlotDto AvailableSlotDto = new AvailableSlotDto();
	List<AvailableSlotDto> bookedSlots = new ArrayList<>();
	
	@Test
	public void testAuthenticateDoctor() throws DoctorNotFoundException {
		Mockito.when(doctorService.authenticateDoctor(loginRequestDto)).thenReturn(loginResponseDto);
		ResponseEntity<LoginResponseDto> actual = doctorController.authenticateDoctor(loginRequestDto);
		assertEquals(HttpStatus.OK, actual.getStatusCode());
	}
	
	@Test
	public void testGetDoctorDetails() throws DoctorNotFoundException {
		doctor.setDoctorId(1);
		Mockito.when(doctorService.getDoctorDetails(1)).thenReturn(doctorDto);
		ResponseEntity<DoctorDto> actual = doctorController.getDoctorDetails(1);
		assertEquals(HttpStatus.OK, actual.getStatusCode());
	}
	
	@Test(expected = DoctorNotFoundException.class)
	public void testGetDoctorDetailsForDoctorNotFoundException() throws DoctorNotFoundException {
		doctor.setDoctorId(null);
		doctorController.getDoctorDetails(null);
	}
	
	@Test
	public void testGetSlotsForPatient() throws DoctorNotFoundException, SlotNotFoundException {
		doctor.setDoctorId(1);
		Mockito.when(doctorService.getSlotsForPatient(1)).thenReturn(availableSlots);
		ResponseEntity<List<SlotDto>> actual = doctorController.getSlotsForPatient(1);
		assertEquals(HttpStatus.OK, actual.getStatusCode());
	}
	
	@Test(expected = DoctorNotFoundException.class)
	public void testGetSlotsForPatientForDoctorNotFoundException() throws DoctorNotFoundException, SlotNotFoundException {
		doctor.setDoctorId(null);
	    doctorController.getSlotsForPatient(null);
	}
	
	@Test
	public void testGetSlotsForDoctor() throws DoctorNotFoundException, SlotNotFoundException {
		doctor.setDoctorId(1);
		Mockito.when(doctorService.getSlotsForDoctor(1)).thenReturn(bookedSlots);
		ResponseEntity<List<AvailableSlotDto>> actual = doctorController.getSlotsForDoctor(1);
		assertEquals(HttpStatus.OK, actual.getStatusCode());
	}
	
	@Test(expected = DoctorNotFoundException.class)
	public void testGetSlotsForDoctorForDoctorNotFoundException() throws DoctorNotFoundException, SlotNotFoundException {
		doctor.setDoctorId(null);
	    doctorController.getSlotsForDoctor(null);
	}

}
