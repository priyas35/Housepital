package com.squad.housepital.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squad.housepital.dto.DoctorSearchResponseDto;
import com.squad.housepital.entity.Hospital;
import com.squad.housepital.entity.Location;
import com.squad.housepital.repository.DoctorSlotRepository;
import com.squad.housepital.repository.HospitalRepository;
import com.squad.housepital.repository.LocationRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LocationServiceImpl implements LocationService {

	@Autowired
	LocationRepository locationRepository;

	@Autowired
	HospitalRepository hospitalRepository;

	@Autowired
	DoctorSlotRepository doctorSlotRepository;

	/**
	 * 
	 * @author Raghu.
	 * @since 2020-02-05. This method will get all the locations
	 * @param none
	 * @return list of all location.
	 * 
	 */
	@Override
	public List<Location> getAllLocations() {
		log.info("LocationServiceImpl getAllLocations ---> getting all locations");
		return locationRepository.findAll();
	}

	/**
	 * 
	 * @author Raghu.
	 * @since 2020-02-05. This method will get all the doctors in a particular
	 *        location and filter the doctors based on name, specialization and
	 *        email
	 * @param location id as Integer and filter name as String.
	 * @return list of doctors for a particular location.
	 * 
	 */
	@Override
	public List<DoctorSearchResponseDto> searchDoctor(Integer locationId, String name) {
		log.info("LocationServiceImpl searchDoctor ---> getting doctors for particular location");
		Location location = new Location();
		location.setLocationId(locationId);
		List<Hospital> hospitals = hospitalRepository.findByLocation(location);
		List<DoctorSearchResponseDto> doctorDtos = new ArrayList<>();

		hospitals.forEach(hospital -> {
			DoctorSearchResponseDto doctorSearchResponseDto = new DoctorSearchResponseDto();
			BeanUtils.copyProperties(doctorSlotRepository.findByHospital(hospital).getDoctor(),
					doctorSearchResponseDto);
			doctorDtos.add(doctorSearchResponseDto);
		});
		if (name.isEmpty()) {
			log.info("LocationServiceImpl searchDoctor ---> getting doctors for particular location without filter");
			return doctorDtos;
		}
		log.info("LocationServiceImpl searchDoctor ---> getting doctors for particular location applying filter");
		return doctorDtos.stream()
				.filter(doctorDto -> doctorDto.getDoctorName().contains(name)
						|| doctorDto.getSpecialization().contains(name) || doctorDto.getEmail().contains(name))
				.collect(Collectors.toList());
	}

}
