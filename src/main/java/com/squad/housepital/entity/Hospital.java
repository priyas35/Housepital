package com.squad.housepital.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Hospital {
	
	@Id
	private Integer hospitalId;
	private String hospitalName;
	
	@OneToOne
	private Location location;
	
	private String address;
	
}
