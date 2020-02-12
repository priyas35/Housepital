package com.squad.housepital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squad.housepital.entity.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer>{

	Optional<Doctor> findByMobileAndPassword(Long mobile, String password);

}
