package com.squad.housepital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squad.housepital.entity.DoctorSlot;
import com.squad.housepital.entity.Hospital;

@Repository
public interface DoctorSlotRepository extends JpaRepository<DoctorSlot, Integer>{

	Optional<DoctorSlot> findByHospital(Hospital hospitals);

}
