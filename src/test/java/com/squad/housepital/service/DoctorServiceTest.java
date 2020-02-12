package com.squad.housepital.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.squad.housepital.constant.Constant;
import com.squad.housepital.dto.AppointmentRequestDto;
import com.squad.housepital.dto.AvailableSlotDto;
import com.squad.housepital.dto.DoctorDto;
import com.squad.housepital.dto.LoginRequestDto;
import com.squad.housepital.dto.LoginResponseDto;
import com.squad.housepital.dto.ResponseDto;
import com.squad.housepital.dto.SlotDto;
import com.squad.housepital.entity.Doctor;
import com.squad.housepital.entity.DoctorSlot;
import com.squad.housepital.entity.Hospital;
import com.squad.housepital.entity.Location;
import com.squad.housepital.entity.Patient;
import com.squad.housepital.exception.DoctorNotFoundException;
import com.squad.housepital.exception.HospitalNotFoundException;
import com.squad.housepital.exception.SlotNotFoundException;
import com.squad.housepital.repository.DoctorRepository;
import com.squad.housepital.repository.DoctorSlotRepository;
import com.squad.housepital.repository.HospitalRepository;



@RunWith(MockitoJUnitRunner.Silent.class)
public class DoctorServiceTest {

	@InjectMocks
	DoctorServiceImpl doctorServiceImpl;

	@Mock
	DoctorRepository doctorRepository;

	@Mock
	DoctorSlotRepository doctorSlotRepository;
	
	@Mock
	HospitalRepository hospitalRepository;

	LoginRequestDto loginRequestDto = new LoginRequestDto();
	LoginResponseDto loginResponseDto = new LoginResponseDto();
	Doctor doctor = new Doctor();
	DoctorDto doctorDto = new DoctorDto();
	SlotDto SlotDto = new SlotDto();
	List<SlotDto> availableSlots = new ArrayList<>();
	AvailableSlotDto AvailableSlotDto = new AvailableSlotDto();
	List<AvailableSlotDto> bookedSlots = new ArrayList<>();
	DoctorSlot doctorSlot = new DoctorSlot();
	List<DoctorSlot> doctorSlots = new ArrayList<>();
	Hospital hospital = new Hospital();
	Location location = new Location();
	Patient patient = new Patient();
	AppointmentRequestDto appointmentRequestDto= new AppointmentRequestDto();
	ResponseDto responseDto= new ResponseDto();
	@Before
	public void init() {
		doctor.setDoctorId(1);
		doctor.setMobile(1L);
		doctor.setPassword("sri");
		doctor.setDoctorName("sri");
		hospital.setHospitalId(1);
		location.setLocationId(1);
		hospital.setLocation(location);
		loginRequestDto.setMobile(1L);
		loginRequestDto.setPassword("sri");
		loginResponseDto.setDoctorId(1);
		doctorDto.setDoctorName("sri");
		doctorSlot.setDoctorSlotId(1);
		doctorSlot.setDoctor(doctor);
		doctorSlot.setHospital(hospital);
		doctorSlot.setPatient(patient);
		doctorSlot.setAvailability(Constant.AVAILABLE);
		doctorSlots.add(doctorSlot);
		
		appointmentRequestDto.setDoctorId(1);
		appointmentRequestDto.setHospitalId(1);
		appointmentRequestDto.setFromTime("18:00");
		appointmentRequestDto.setSlotToTime("19:00");
		appointmentRequestDto.setDate(LocalDate.parse("2019-02-12"));
	}

	@Test
	public void testAuthenticateDoctor() throws DoctorNotFoundException {
		Mockito.when(doctorRepository.findByMobileAndPassword(1L, "sri")).thenReturn(Optional.of(doctor));
		LoginResponseDto actual = doctorServiceImpl.authenticateDoctor(loginRequestDto);
		assertEquals(1, actual.getDoctorId());
	}

	@Test(expected = DoctorNotFoundException.class)
	public void testAuthenticateDoctorForDoctorNotFoundException() throws DoctorNotFoundException {
		loginRequestDto = new LoginRequestDto();
		doctorServiceImpl.authenticateDoctor(loginRequestDto);
	}

	@Test
	public void testGetDoctorDetails() throws DoctorNotFoundException {
		Mockito.when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));
		DoctorDto actual = doctorServiceImpl.getDoctorDetails(1);
		assertEquals("sri", actual.getDoctorName());
	}

	@Test(expected = DoctorNotFoundException.class)
	public void testGetDoctorDetailsForDoctorNotFoundException() throws DoctorNotFoundException {
		Mockito.when(doctorRepository.findById(5)).thenReturn(Optional.of(doctor));
		doctorServiceImpl.getDoctorDetails(7);
	}

	@Test
	public void testGetSlotsForPatient() throws DoctorNotFoundException, SlotNotFoundException {
		Mockito.when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));
		Mockito.when(doctorSlotRepository.findByDoctorAndAvailability(doctor, Constant.AVAILABLE))
				.thenReturn(doctorSlots);
		List<SlotDto> actual = doctorServiceImpl.getSlotsForPatient(1);
		assertEquals(1, actual.size());
	}

	@Test
	public void testGetSlotsForDoctor() throws DoctorNotFoundException, SlotNotFoundException {
		doctorSlot.setAvailability(Constant.UN_AVAILABLE);
		Mockito.when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));
		Mockito.when(doctorSlotRepository.findByDoctorAndAvailability(doctor, Constant.UN_AVAILABLE))
				.thenReturn(doctorSlots);
		List<AvailableSlotDto> actual = doctorServiceImpl.getSlotsForDoctor(1);
		assertEquals(1, actual.size());
	}

	@Test(expected = DoctorNotFoundException.class)
	public void testGetSlotsForPatientForDoctorNotFoundException()
			throws DoctorNotFoundException, SlotNotFoundException {
		Mockito.when(doctorRepository.findById(2)).thenReturn(Optional.of(doctor));
		Mockito.when(doctorSlotRepository.findByDoctorAndAvailability(doctor, Constant.AVAILABLE))
				.thenReturn(doctorSlots);
		doctorServiceImpl.getSlotsForPatient(1);
	}
	
	@Test(expected = SlotNotFoundException.class)
	public void testGetSlotsForPatientForSlotNotFoundException()
			throws DoctorNotFoundException, SlotNotFoundException {
		doctorSlots = new ArrayList<>();
		Mockito.when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));
		Mockito.when(doctorSlotRepository.findByDoctorAndAvailability(doctor, Constant.AVAILABLE))
				.thenReturn(doctorSlots);
		doctorServiceImpl.getSlotsForPatient(1);
	}
	
	@Test(expected = DoctorNotFoundException.class)
	public void testGetSlotsForDoctorForDoctorNotFoundException()
			throws DoctorNotFoundException, SlotNotFoundException {
		Mockito.when(doctorRepository.findById(2)).thenReturn(Optional.of(doctor));
		Mockito.when(doctorSlotRepository.findByDoctorAndAvailability(doctor, Constant.UN_AVAILABLE))
				.thenReturn(doctorSlots);
		doctorServiceImpl.getSlotsForDoctor(1);
	}
	
	@Test(expected = SlotNotFoundException.class)
	public void testGetSlotsForDoctorForSlotNotFoundException()
			throws DoctorNotFoundException, SlotNotFoundException {
		doctorSlots = new ArrayList<>();
		Mockito.when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));
		Mockito.when(doctorSlotRepository.findByDoctorAndAvailability(doctor, Constant.UN_AVAILABLE))
				.thenReturn(doctorSlots);
		doctorServiceImpl.getSlotsForDoctor(1);
	}
	
	@Test(expected=DoctorNotFoundException.class)
	public void testAddAppointmentSlotDoctorNotFound() throws DoctorNotFoundException, HospitalNotFoundException {
		Mockito.when(doctorRepository.findById(6)).thenReturn(Optional.of(doctor));
		doctorServiceImpl.addAppointmentSlot(appointmentRequestDto);
	}

	@Test(expected=HospitalNotFoundException.class)
	public void testAddAppointmentSlotHospitalNotFound() throws DoctorNotFoundException, HospitalNotFoundException {
		Mockito.when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));
		Mockito.when(hospitalRepository.findById(6)).thenReturn(Optional.of(hospital));
		doctorServiceImpl.addAppointmentSlot(appointmentRequestDto);
	}
	
	@Test
	public void testAddAppointmentSlot() throws DoctorNotFoundException, HospitalNotFoundException {
		Mockito.when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));
		Mockito.when(hospitalRepository.findById(1)).thenReturn(Optional.of(hospital));
		ResponseDto responseDto=doctorServiceImpl.addAppointmentSlot(appointmentRequestDto);
		Assert.assertNotNull(responseDto);
	}
	
	@Test
	public void testAddAppointmentSlotAlreadyPresent()throws DoctorNotFoundException, HospitalNotFoundException {
		Mockito.when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));
		Mockito.when(hospitalRepository.findById(1)).thenReturn(Optional.of(hospital));
		Mockito.when(doctorSlotRepository.findByDoctorAndDateAndSlotTime(doctor,LocalDate.parse("2019-02-12"),LocalTime.parse("18:00"))).thenReturn(Optional.of(doctorSlot));
		ResponseDto responseDto=doctorServiceImpl.addAppointmentSlot(appointmentRequestDto);
		Assert.assertNotNull(responseDto);
	}
}
