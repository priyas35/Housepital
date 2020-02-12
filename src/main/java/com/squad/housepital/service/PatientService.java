package com.squad.housepital.service;

import com.squad.housepital.dto.BookSlotRequestDto;
import com.squad.housepital.dto.ResponseDto;
import com.squad.housepital.exception.SlotNotFoundException;

public interface PatientService {
	
	ResponseDto bookSlot(BookSlotRequestDto bookSlotRequestDto) throws SlotNotFoundException;

}
