package com.squad.housepital.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.squad.housepital.entity.Hospital;
import com.squad.housepital.repository.HospitalRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class HospitalServiceTest {

	@InjectMocks
	HospitalServiceImpl hospitalServiceImpl;

	@Mock
	HospitalRepository hospitalRepository;

	Hospital hospital = new Hospital();
	List<Hospital> hospitals = new ArrayList<>();

	@Before
	public void setUp() {
		hospital.setHospitalId(1);
		hospitals.add(hospital);

	}

	@Test
	public void testGetAllHospitals() {
		Mockito.when(hospitalRepository.findAll()).thenReturn(hospitals);
		List<Hospital> actual = hospitalServiceImpl.getHospitalList();
		assertEquals(1, actual.size());
	}

}
