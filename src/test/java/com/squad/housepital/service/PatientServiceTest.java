package com.squad.housepital.service;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.squad.housepital.dto.BookSlotRequestDto;
import com.squad.housepital.entity.Doctor;
import com.squad.housepital.entity.DoctorSlot;
import com.squad.housepital.entity.Hospital;
import com.squad.housepital.entity.Location;
import com.squad.housepital.entity.Patient;
import com.squad.housepital.exception.SlotNotFoundException;
import com.squad.housepital.repository.DoctorSlotRepository;
import com.squad.housepital.repository.PatientRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class PatientServiceTest {

	@InjectMocks
	PatientServiceImpl patientService;

	@Mock
	PatientRepository patientRepository;

	@Mock
	DoctorSlotRepository doctorSlotRepository;

	BookSlotRequestDto bookSlotRequestDto = new BookSlotRequestDto();
	DoctorSlot doctorSlot = new DoctorSlot();
	Doctor doctor = new Doctor();
	Location location = new Location();
	Hospital hospital = new Hospital();

	@Before
	public void setup() {
		bookSlotRequestDto.setDoctorSlotId(1);
		bookSlotRequestDto.setEmail("test");
		bookSlotRequestDto.setMobile(1L);
		bookSlotRequestDto.setPatientName("test");

		location.setLocationId(1);
		location.setLocationName("test");
		hospital.setLocation(location);
		hospital.setHospitalId(1);
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

		doctorSlot.setAvailability("yes");
		doctorSlot.setDate(LocalDate.now());
		doctorSlot.setDoctor(doctor);
		doctorSlot.setDoctorSlotId(1);
		doctorSlot.setHospital(hospital);
		doctorSlot.setSlotTime(LocalTime.now());
	}

	@Test(expected = SlotNotFoundException.class)
	public void testBookSlotSlotNotFoundException() throws SlotNotFoundException {
		Mockito.when(doctorSlotRepository.findById(bookSlotRequestDto.getDoctorSlotId()))
				.thenReturn(Optional.ofNullable(null));
		patientService.bookSlot(bookSlotRequestDto);
		
	}
	
	@Test
	public void testBookSlotSuccess() throws SlotNotFoundException {
		Mockito.when(doctorSlotRepository.findById(bookSlotRequestDto.getDoctorSlotId()))
				.thenReturn(Optional.ofNullable(doctorSlot));
		Mockito.when(patientRepository.save(Mockito.any())).thenReturn(new Patient());
		Mockito.when(doctorSlotRepository.save(Mockito.any())).thenReturn(new DoctorSlot());
		assertNotNull(patientService.bookSlot(bookSlotRequestDto));
		
	}

}
