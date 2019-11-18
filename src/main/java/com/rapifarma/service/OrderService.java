package com.rapifarma.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rapifarma.model.entity.Order;

public interface OrderService extends CrudService<Order, Long>{
	public Page<Order> findAll(Pageable pageable) throws Exception;
	public Page<Order> fetchByName(String name,Pageable pageable) throws Exception;
}
