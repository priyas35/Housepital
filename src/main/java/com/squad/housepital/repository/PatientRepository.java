package com.squad.housepital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squad.housepital.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer>{

}
