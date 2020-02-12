package com.squad.housepital.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.squad.housepital.entity.Doctor;
import com.squad.housepital.entity.DoctorSlot;
import com.squad.housepital.entity.Hospital;
import com.squad.housepital.entity.Location;
import com.squad.housepital.repository.DoctorRepository;
import com.squad.housepital.repository.DoctorSlotRepository;
import com.squad.housepital.repository.HospitalRepository;
import com.squad.housepital.repository.LocationRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class LocationServiceImplTest {
	
	@InjectMocks
	LocationServiceImpl locationService;
	
	@Mock
	LocationRepository locationRepository;
	
	@Mock
	HospitalRepository hospitalRepository;
	
	@Mock
	DoctorSlotRepository doctorSlotRepository;
	
	@Mock
	DoctorRepository doctorRepository;
	
	Location location = new Location();
	Hospital hospital = new Hospital();
	List<Hospital> hospitals = new ArrayList<>();
	DoctorSlot doctorSlot = new DoctorSlot();
	List<DoctorSlot> doctorSlots = new ArrayList<>();
	Doctor doctor = new Doctor();
	List<Doctor> doctors = new ArrayList<>();
	List<Integer> ids = new ArrayList<>();
	
	@Before
	public void setup() {
		location.setLocationId(1);
		location.setLocationName("test");
		hospital.setLocation(location);
		hospital.setHospitalId(1);
		hospitals.add(hospital);
		doctor.setConsultationFee(1.0);
		doctor.setDoctorId(1);
		doctor.setDoctorName("a");
		doctor.setEmail("c");
		doctor.setExperience(1);
		doctor.setImageUrl("test");
		doctor.setMobile(1L);
		doctor.setPassword("test");
		doctor.setRating(1.0);
		doctor.setSpecialization("b");
		doctors.add(doctor);
		doctorSlot.setAvailability("yes");
		doctorSlot.setDate(LocalDate.now());
		doctorSlot.setDoctor(doctor);
		doctorSlot.setDoctorSlotId(1);
		doctorSlot.setHospital(hospital);
		doctorSlot.setSlotTime(LocalTime.now());
		
		doctorSlots.add(doctorSlot);
		
		
	}
	
	@Test
	public void testGetAllLocationsSuccess() {
		
		Mockito.when(locationRepository.findAll()).thenReturn(new ArrayList<>());
		assertNotNull(locationService.getAllLocations());
		
	}
	
	@Test
	public void testSearchDoctorSlotNull() {
		Mockito.when(hospitalRepository.findByLocation(Mockito.any())).thenReturn(hospitals);
		Mockito.when(doctorSlotRepository.findByHospitalAndAvailability(Mockito.any(), Mockito.any())).thenReturn(doctorSlots);
		Mockito.when(doctorRepository.findAllById(Mockito.any())).thenReturn(doctors);
		Integer actual = locationService.searchDoctor(1, "test").size();
		Integer expected = 0;
		assertEquals(expected, actual);
	}
	
	@Test
	public void testSearchDoctorNameNull() {
		Mockito.when(hospitalRepository.findByLocation(Mockito.any())).thenReturn(hospitals);
		Mockito.when(doctorSlotRepository.findByHospitalAndAvailability(Mockito.any(), Mockito.any())).thenReturn(doctorSlots);
		Mockito.when(doctorRepository.findAllById(Mockito.any())).thenReturn(doctors);
		Integer actual = locationService.searchDoctor(1, "").get(0).getDoctorId();
		Integer expected = 1;
		assertEquals(expected, actual);
	}
	
	@Test
	public void testSearchDoctorName() {
		Mockito.when(hospitalRepository.findByLocation(Mockito.any())).thenReturn(hospitals);
		Mockito.when(doctorSlotRepository.findByHospitalAndAvailability(Mockito.any(), Mockito.any())).thenReturn(doctorSlots);
		Mockito.when(doctorRepository.findAllById(Mockito.any())).thenReturn(doctors);
		Integer actual = locationService.searchDoctor(1, "a").get(0).getDoctorId();
		Integer expected = 1;
		assertEquals(expected, actual);
	}
	
	@Test
	public void testSearchDoctorSpecialization() {
		Mockito.when(hospitalRepository.findByLocation(Mockito.any())).thenReturn(hospitals);
		Mockito.when(doctorSlotRepository.findByHospitalAndAvailability(Mockito.any(), Mockito.any())).thenReturn(doctorSlots);
		Mockito.when(doctorRepository.findAllById(Mockito.any())).thenReturn(doctors);
		Integer actual = locationService.searchDoctor(1, "b").get(0).getDoctorId();
		Integer expected = 1;
		assertEquals(expected, actual);
	}
	
	@Test
	public void testSearchDoctorEmail() {
		Mockito.when(hospitalRepository.findByLocation(Mockito.any())).thenReturn(hospitals);
		Mockito.when(doctorSlotRepository.findByHospitalAndAvailability(Mockito.any(), Mockito.any())).thenReturn(doctorSlots);
		Mockito.when(doctorRepository.findAllById(Mockito.any())).thenReturn(doctors);
		Integer actual = locationService.searchDoctor(1, "c").get(0).getDoctorId();
		Integer expected = 1;
		assertEquals(expected, actual);
	}
	
	@Test
	public void testSearchDoctorNone() {
		Mockito.when(hospitalRepository.findByLocation(Mockito.any())).thenReturn(hospitals);
		Mockito.when(doctorSlotRepository.findByHospitalAndAvailability(Mockito.any(), Mockito.any())).thenReturn(doctorSlots);
		Mockito.when(doctorRepository.findAllById(Mockito.any())).thenReturn(doctors);
		Integer actual = locationService.searchDoctor(1, "d").size();
		Integer expected = 0;
		assertEquals(expected, actual);
	}

}
