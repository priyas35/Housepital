package com.squad.housepital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.squad.housepital.constant.Constant;
import com.squad.housepital.dto.BookSlotRequestDto;
import com.squad.housepital.dto.ResponseDto;
import com.squad.housepital.exception.SlotNotFoundException;
import com.squad.housepital.service.PatientService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/patients")
@CrossOrigin
@Slf4j
public class PatientController {

	@Autowired
	PatientService patientService;

	/**
	 * 
	 * @author Raghu.
	 * 
	 *         This method will book the slot for patient and it will save the
	 *         patient detail;
	 * 
	 * @since 2020-02-05
	 * @param patient name, email , mobile and slotId will be the input.
	 * @throws SlotNotFoundException
	 * @ @return status of saving.
	 * 
	 */
	@PostMapping
	public ResponseEntity<ResponseDto> bookSlot(@RequestBody BookSlotRequestDto bookSlotRequestDto)
			throws SlotNotFoundException {
		log.info("PatientController bookSlot ---> booking slot");
		ResponseDto responseDto = patientService.bookSlot(bookSlotRequestDto);
		responseDto.setStatusCode(HttpStatus.OK.value());
		responseDto.setStatusMessage(Constant.SUCCESS_MESSAGE);
		return ResponseEntity.ok().body(responseDto);
	}

}
