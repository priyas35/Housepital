package com.squad.housepital.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.squad.housepital.service.LocationService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class LocationControllerTest {
	
	@InjectMocks
	LocationController locationController;
	
	@Mock
	LocationService locationService;
	
	@Test
	public void testGetAllLocationSuccess() {
		Mockito.when(locationService.getAllLocations()).thenReturn(new ArrayList<>());
		Integer actual = locationController.getAllLocation().getStatusCodeValue();
		Integer expected = 200;
		assertEquals(expected, actual);
	}
	
	@Test
	public void testSearchDoctorSuccess() {
		Mockito.when(locationService.searchDoctor(1, "test")).thenReturn(new ArrayList<>());
		Integer actual = locationController.searchDoctor(1, "test").getStatusCodeValue();
		Integer expected = 200;
		assertEquals(expected, actual);
	}

}
