package com.rapifarma.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rapifarma.model.entity.Patient;

public interface PatientService extends CrudService<Patient, Long>{
	public Page<Patient> findAll(Pageable pageable) throws Exception;	
	public Page<Patient> fetchByName(String name,Pageable pageable) throws Exception;
}
