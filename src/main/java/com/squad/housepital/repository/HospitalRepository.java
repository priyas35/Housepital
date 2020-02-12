package com.squad.housepital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squad.housepital.entity.Hospital;
import com.squad.housepital.entity.Location;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Integer>{

	List<Hospital> findByLocation(Location location);

}
