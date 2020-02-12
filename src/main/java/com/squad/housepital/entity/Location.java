package com.squad.housepital.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Location {
	
	@Id
	private Integer locationId;
	
	private String locationName;

}
