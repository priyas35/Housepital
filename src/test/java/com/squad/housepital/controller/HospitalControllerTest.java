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

import com.squad.housepital.controller.HospitalController;
import com.squad.housepital.entity.Hospital;
import com.squad.housepital.service.HospitalService;

@RunWith(MockitoJUnitRunner.class)
public class HospitalControllerTest {
	
	@InjectMocks
	HospitalController hospitalController;
	
	@Mock
	HospitalService hospitalService;
	
	@Test
	public void testGetAllHospitals() {
		Mockito.when(hospitalService.getHospitalList()).thenReturn(new ArrayList<>());
		ResponseEntity<List<Hospital>> actual = hospitalController.getHospitals();
		assertEquals(HttpStatus.OK, actual.getStatusCode());
	}

}
