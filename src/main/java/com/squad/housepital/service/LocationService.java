package com.squad.housepital.service;

import java.util.List;

import com.squad.housepital.dto.DoctorSearchResponseDto;
import com.squad.housepital.entity.Location;

public interface LocationService {
	
	List<Location> getAllLocations();
	
	List<DoctorSearchResponseDto> searchDoctor(Integer locationId, String name);

}
