package com.rapifarma.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rapifarma.exception.ResourceNotFoundException;
import com.rapifarma.model.entity.Patient;
import com.rapifarma.model.repository.PatientRepository;
import com.rapifarma.service.PatientService;

@Service
public class PatientServiceImpl implements PatientService{

	@Autowired
	private PatientRepository patientRepository;
	
	@Override
	public List<Patient> getAll() throws Exception {
		List<Patient> patients = new ArrayList<>();
		this.patientRepository.findAll().iterator().forEachRemaining(patients::add);
		return patients;
	}

	@Override
	public Patient getOneById(Long id) throws Exception {
		Optional<Patient> patient = this.patientRepository.findById(id);
		if(!patient.isPresent()) {
			throw new ResourceNotFoundException("There is not Patient with ID = " + id);
		}
		return patient.get();		
	}

	@Transactional
	@Override
	public Patient create(Patient entity) throws Exception {
		return this.patientRepository.save(entity);
	}

	@Transactional
	@Override
	public Patient update(Long id, Patient entity) throws Exception {
		Patient currentPatient = this.getOneById(id);
		currentPatient.setName(entity.getName());
		currentPatient.setFirstsurname(entity.getFirstsurname());
		currentPatient.setSecondsurname(entity.getSecondsurname());
		currentPatient.setDni(entity.getDni());
		currentPatient.setSex(entity.getSex());
		return this.patientRepository.save(currentPatient);
	}

	@Transactional
	@Override
	public void delete(Long id) throws Exception {
		this.patientRepository.deleteById(id);
	}

	@Override
	public Page<Patient> findAll(Pageable pageable) throws Exception {
		return this.patientRepository.findAll(pageable);
	}

	@Override
	public Page<Patient> fetchByName(String name, Pageable pageable) throws Exception {
		return this.patientRepository.fetchByName(name, pageable);
	}
	
}
