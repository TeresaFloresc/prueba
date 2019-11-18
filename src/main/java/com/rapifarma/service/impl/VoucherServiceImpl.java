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
import com.rapifarma.model.entity.Voucher;
import com.rapifarma.model.entity.VoucherDetail;
import com.rapifarma.model.repository.VoucherRepository;
import com.rapifarma.service.VoucherService;

@Service
public class VoucherServiceImpl implements VoucherService{

	@Autowired
	private VoucherRepository voucherRepository;
	
	@Override
	public List<Voucher> getAll() throws Exception {
		List<Voucher> vouchers = new ArrayList<>();
		this.voucherRepository.findAll().iterator().forEachRemaining(vouchers::add);
		return vouchers;
	}

	@Override
	public Voucher getOneById(Long id) throws Exception {
		Optional<Voucher> voucher = this.voucherRepository.findById(id);
		if(!voucher.isPresent()) {
			throw new ResourceNotFoundException("There is not voucher with ID = " + id);
		}
		return voucher.get();
	}

	@Transactional
	@Override
	public Voucher create(Voucher entity) throws Exception {
		entity.getVoucherDetails().forEach(item->
				item.setVoucher(entity));		
		return this.voucherRepository.save(entity);
	}

	@Transactional
	@Override
	public Voucher update(Long id, Voucher entity) throws Exception {
		Voucher currentVoucher = this.getOneById(id);
		currentVoucher.setNumber(entity.getNumber());
		currentVoucher.setDateEmission(entity.getDateEmission());
		currentVoucher.setIgv(entity.getIgv());
		currentVoucher.setObservation(entity.getObservation());
		currentVoucher.setPatient(entity.getPatient());
		currentVoucher.setSale_value(entity.getSale_value());
		currentVoucher.setTotal_import(entity.getTotal_import());
		currentVoucher.setVoucherDetails(entity.getVoucherDetails());
		return this.voucherRepository.save(currentVoucher);
	}

	@Transactional
	@Override
	public void delete(Long id) throws Exception {
		this.voucherRepository.deleteById(id);		
	}

	@Override
	public Page<Voucher> findAll(Pageable pageable) throws Exception {
		return this.voucherRepository.findAll(pageable);
	}

	@Override
	public Page<Voucher> fetchByNamePatients(String name, Pageable pageable) throws Exception {
		return this.voucherRepository.fetchByNamePatients(name, pageable);
	}

}
