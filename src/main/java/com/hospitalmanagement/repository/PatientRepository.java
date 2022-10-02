package com.hospitalmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hospitalmanagement.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>{
	
}
