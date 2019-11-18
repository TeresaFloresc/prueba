package com.rapifarma.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.rapifarma.model.entity.Supplier;

@Repository
public interface SupplierRepository extends PagingAndSortingRepository<Supplier, Long>{
	public Page<Supplier> findAll(Pageable pageable);
	@Query("SELECT s FROM Supplier s WHERE s.name LIKE %?1%")
	public Page<Supplier> fetchByName(String name, Pageable pageable);
}
