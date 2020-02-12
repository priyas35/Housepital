package com.squad.housepital.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.squad.housepital.dto.BookSlotRequestDto;
import com.squad.housepital.dto.ResponseDto;
import com.squad.housepital.exception.SlotNotFoundException;
import com.squad.housepital.service.PatientService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class PatientControllerTest {
	
	@InjectMocks
	PatientController patientController;
	
	@Mock
	PatientService patientService;
	
	
	@Test
	public void testBookSlotSuccess() throws SlotNotFoundException {
		Mockito.when(patientService.bookSlot(Mockito.any())).thenReturn(new ResponseDto());
		Integer actual = patientController.bookSlot(new BookSlotRequestDto()).getStatusCodeValue();
		Integer expected = 200;
		assertEquals(expected, actual);
	}
	

}
