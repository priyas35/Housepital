package com.squad.housepital.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.squad.housepital.entity.Doctor;
import com.squad.housepital.entity.DoctorSlot;
import com.squad.housepital.entity.Hospital;

@Repository
public interface DoctorSlotRepository extends JpaRepository<DoctorSlot, Integer> {

	List<DoctorSlot> findByDoctorAndAvailability(Doctor doctor, String availability);

	Optional<DoctorSlot> findByHospital(Hospital hospitals);

	DoctorSlot findByDoctorAndSlotTimeAndDate(Doctor doctor, LocalTime slotTime, LocalDate date);

	List<DoctorSlot> findByHospitalAndAvailability(Hospital hospital, String available);
	
	Optional<DoctorSlot> findByDoctorAndDateAndSlotTime(Doctor doctor, LocalDate date, LocalTime fromTime);

}
