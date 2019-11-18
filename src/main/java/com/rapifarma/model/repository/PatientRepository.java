package com.rapifarma.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.rapifarma.model.entity.Patient;

@Repository
public interface PatientRepository extends PagingAndSortingRepository<Patient, Long>{
	public Page<Patient> findAll(Pageable pageable);
	@Query("SELECT p FROM Patient p WHERE p.name LIKE %?1%")
	public Page<Patient> fetchByName(String name,Pageable pageable);
}
