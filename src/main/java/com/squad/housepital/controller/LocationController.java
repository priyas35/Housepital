package com.squad.housepital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.squad.housepital.dto.DoctorSearchResponseDto;
import com.squad.housepital.entity.Location;
import com.squad.housepital.service.LocationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/locations")
@CrossOrigin
@Slf4j
public class LocationController {

	@Autowired
	LocationService locationService;

	/**
	 * 
	 * @author Raghu.
	 * 
	 *         This method will get all the locations
	 * 
	 * @since 2020-02-05.
	 * @param none
	 * @return list of all location.
	 * 
	 */
	@GetMapping
	public ResponseEntity<List<Location>> getAllLocation() {
		log.info("LocationServiceImpl getAllLocations ---> calling locationService");
		return ResponseEntity.ok().body(locationService.getAllLocations());
	}

	/**
	 * 
	 * @author Raghu.
	 * 
	 *         This method will get all the doctors in a particular location and
	 *         filter the doctors based on name, specialization and email
	 * 
	 * @since 2020-02-05.
	 * @param location id as Integer and filter name as String.
	 * @return list of doctors for a particular location.
	 * 
	 */
	@GetMapping("/{locationId}/doctors")
	public ResponseEntity<List<DoctorSearchResponseDto>> searchDoctor(@PathVariable("locationId") Integer locationId,
			@RequestParam("name") String name) {
		log.info("LocationServiceImpl searchDoctor ---> calling locationService");
		return ResponseEntity.ok().body(locationService.searchDoctor(locationId, name));
	}

}
