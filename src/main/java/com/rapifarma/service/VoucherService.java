package com.rapifarma.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rapifarma.model.entity.Voucher;

public interface VoucherService extends CrudService<Voucher, Long>{
	public Page<Voucher> findAll(Pageable pageable) throws Exception;
	public Page<Voucher> fetchByNamePatients(String name,Pageable pageable) throws Exception; 
}
