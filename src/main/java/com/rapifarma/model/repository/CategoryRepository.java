package com.rapifarma.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.rapifarma.model.entity.Category;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long>{
	public Page<Category> findAll(Pageable pageable);	
	@Query("SELECT c FROM Category c WHERE c.name LIKE %?1%")
	public Page<Category> fetchByName(String name,Pageable pageable);
	
}
