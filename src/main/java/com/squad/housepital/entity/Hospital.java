package com.squad.housepital.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Hospital {
	
	@Id
	private Integer hospitalId;
	private String hospitalName;
	
	@OneToOne
	@JoinColumn(name = "location_id")
	private Location location;
	
	private String address;
	
}
