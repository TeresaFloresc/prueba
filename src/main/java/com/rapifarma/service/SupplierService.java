package com.rapifarma.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rapifarma.model.entity.Supplier;

public interface SupplierService extends CrudService<Supplier, Long>{
	public Page<Supplier> findAll(Pageable pageable) throws Exception;
	public Page<Supplier> fetchByName(String name, Pageable pageable) throws Exception;
}
