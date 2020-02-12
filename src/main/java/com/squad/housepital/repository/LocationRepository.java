package com.squad.housepital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squad.housepital.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer>{

}
