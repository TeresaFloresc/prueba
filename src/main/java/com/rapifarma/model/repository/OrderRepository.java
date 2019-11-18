package com.rapifarma.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.rapifarma.model.entity.Order;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long>{
	public Page<Order> findAll(Pageable pageable);
	@Query("SELECT o FROM Order o WHERE o.name LIKE %?1%")
	public Page<Order> fetchByName(String name,Pageable pageable);
}
