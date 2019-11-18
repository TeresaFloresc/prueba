package com.rapifarma.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rapifarma.exception.ResourceNotFoundException;
import com.rapifarma.model.entity.Supplier;
import com.rapifarma.model.repository.SupplierRepository;
import com.rapifarma.service.SupplierService;

@Service
public class SupplierServiceImpl implements SupplierService{

	@Autowired
	private SupplierRepository supplierRepository;
	
	@Override
	public List<Supplier> getAll() throws Exception {
		List<Supplier> suppliers = new ArrayList<>();
		this.supplierRepository.findAll().iterator().forEachRemaining(suppliers::add);
		return suppliers;
	}

	@Override
	public Page<Supplier> fetchByName(String name, Pageable pageable) throws Exception {
		return this.supplierRepository.fetchByName(name, pageable);
	}
	
	@Override
	public Supplier getOneById(Long id) throws Exception {
		Optional<Supplier> supplier = this.supplierRepository.findById(id);
		if(!supplier.isPresent()) {
			throw new ResourceNotFoundException("There is not Supplier with ID = " + id);
		}
		return supplier.get();
	}

	@Override
	public Supplier create(Supplier entity) throws Exception {
		return this.supplierRepository.save(entity);		
	}

	@Override
	public Supplier update(Long id, Supplier entity) throws Exception {
		Supplier currentSupplier = this.getOneById(id);
		currentSupplier.setAdress(entity.getAdress());
		currentSupplier.setContact(entity.getContact());
		currentSupplier.setEmail(entity.getEmail());
		currentSupplier.setName(entity.getName());
		currentSupplier.setPhone(entity.getPhone());
		currentSupplier.setRuc(entity.getRuc());
		return this.supplierRepository.save(currentSupplier);
	}

	@Override
	public void delete(Long id) throws Exception {
		this.supplierRepository.deleteById(id);
	}
	
	@Override
	public Page<Supplier> findAll(Pageable pageable) throws Exception{
		return this.supplierRepository.findAll(pageable);
	}

}
