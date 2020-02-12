package com.squad.housepital.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto extends ResponseDto{
	
	private Integer doctorId;
	private String doctorName;

}
