package com.rapifarma.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.rapifarma.model.entity.Product;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long>{
	public Page<Product> findAll(Pageable pageable);
	@Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
	public Page<Product> fetchByName (String name, Pageable pageable);
}
