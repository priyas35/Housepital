package com.squad.housepital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squad.housepital.entity.Doctor;
import com.squad.housepital.entity.DoctorSlot;

@Repository
public interface DoctorSlotRepository extends JpaRepository<DoctorSlot, Integer>{
	
	List<DoctorSlot> findByDoctorAndAvailability(Doctor doctor,String availability);

}
