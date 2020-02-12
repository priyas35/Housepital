package com.squad.housepital.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.squad.housepital.constant.Constant;
import com.squad.housepital.dto.AvailableSlotDto;
import com.squad.housepital.dto.DoctorDto;
import com.squad.housepital.dto.LoginRequestDto;
import com.squad.housepital.dto.LoginResponseDto;
import com.squad.housepital.dto.SlotDto;
import com.squad.housepital.exception.DoctorNotFoundException;
import com.squad.housepital.exception.SlotNotFoundException;
import com.squad.housepital.service.DoctorService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/doctors")
@CrossOrigin
@Slf4j
public class DoctorController {

	@Autowired
	DoctorService doctorService;

	/**
	 * 
	 * @author PriyaDharshini S.
	 * @since 2020-02-11. This method will authenticate the user.
	 * @param loginDto - details of the user login
	 * @return LoginResponseDto which has status message,statusCode,role of the user
	 *         and userDetails.
	 * @throws DoctorNotFoundException it will throw the exception if the doctor is
	 *                                 not registered.
	 * 
	 */
	@PostMapping
	public ResponseEntity<LoginResponseDto> authenticateDoctor(@Valid @RequestBody LoginRequestDto loginRequestDto)
			throws DoctorNotFoundException {
		LoginResponseDto loginResponseDto = doctorService.authenticateDoctor(loginRequestDto);
		log.info("Entering into UserController authenticateUser metod: calling UserService");
		loginResponseDto.setStatusMessage(Constant.AUTHENTICATION_SUCCESSFUL);
		loginResponseDto.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
	}

	/**
	 * @author PriyaDharshini S.
	 * @since 2020-02-12. this method will get the details of the doctor.
	 * @param doctorId - unique id of doctor
	 * @return DoctorDto which has doctorDetails
	 * @throws DoctorNotFoundException it will throw the exception if the doctor is
	 *                                 not registered.
	 * 
	 */
	@GetMapping("/{doctorId}")
	public ResponseEntity<DoctorDto> getDoctorDetails(@PathVariable Integer doctorId) throws DoctorNotFoundException {
		if (doctorId == null) {
			log.debug("Exception occurred in DoctorController getDoctorDetails method:" + Constant.DOCTOR_NOT_FOUND);
			throw new DoctorNotFoundException(Constant.DOCTOR_NOT_FOUND);
		}
		log.info("Entering into DoctorController getDoctorDetails method: calling doctor service");
		return new ResponseEntity<>(doctorService.getDoctorDetails(doctorId), HttpStatus.OK);
	}

	/**
	 * @author PriyaDharshini S.
	 * @since 2020-02-12. this method will get the available slots for the doctor on
	 *        the patient side.
	 * @param doctorId - unique id of doctor
	 * @return list of SlotDto which has available slots and details.
	 * @throws DoctorNotFoundException it will throw the exception if the doctor is
	 *                                 not registered.
	 * 
	 */
	@GetMapping("/{doctorId}/availabilities")
	public ResponseEntity<List<SlotDto>> getSlotsForPatient(@PathVariable Integer doctorId)
			throws DoctorNotFoundException, SlotNotFoundException {
		if (doctorId == null) {
			log.debug("Exception occurred in DoctorController getSlotsForPatient method:" + Constant.DOCTOR_NOT_FOUND);
			throw new DoctorNotFoundException(Constant.DOCTOR_NOT_FOUND);
		}
		log.info("Entering into DoctorController getSlotsForPatient method: calling doctor service");
		return new ResponseEntity<>(doctorService.getSlotsForPatient(doctorId), HttpStatus.OK);
	}

	/**
	 * @author PriyaDharshini S.
	 * @since 2020-02-12. this method will get the booked slots for the doctor on
	 *        the doctor side.
	 * @param doctorId - unique id of doctor
	 * @return list of AvailableSlotDto which has booked slots and details.
	 * @throws DoctorNotFoundException it will throw the exception if the doctor is
	 *                                 not registered.
	 * 
	 */
	@GetMapping("/{doctorId}/appointments")
	public ResponseEntity<List<AvailableSlotDto>> getSlotsForDoctor(@PathVariable Integer doctorId)
			throws DoctorNotFoundException, SlotNotFoundException {
		if (doctorId == null) {
			log.debug("Exception occurred in DoctorController getSlotsForDoctor method:" + Constant.DOCTOR_NOT_FOUND);
			throw new DoctorNotFoundException(Constant.DOCTOR_NOT_FOUND);
		}
		log.info("Entering into DoctorController getSlotsForDoctor method: calling doctor service");
		return new ResponseEntity<>(doctorService.getSlotsForDoctor(doctorId), HttpStatus.OK);
	}

}
