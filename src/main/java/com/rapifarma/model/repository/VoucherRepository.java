package com.rapifarma.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.rapifarma.model.entity.Voucher;

@Repository
public interface VoucherRepository extends PagingAndSortingRepository<Voucher, Long>{
	public Page<Voucher> findAll(Pageable pageable);
	@Query(nativeQuery = true, value = 
			"SELECT * FROM VOUCHERS WHERE VOUCHERS.PATIENT_ID IN (SELECT PATIENT_ID FROM PATIENTS WHERE NAME LIKE %?1%)")
	public Page<Voucher> fetchByNamePatients(String name,Pageable pageable);	
}
