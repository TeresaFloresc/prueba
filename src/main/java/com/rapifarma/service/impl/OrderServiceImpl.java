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
import com.rapifarma.model.entity.Order;
import com.rapifarma.model.repository.OrderRepository;
import com.rapifarma.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderRepository orderRepository;
	
	@Override
	public List<Order> getAll() throws Exception {
		List<Order> orders = new ArrayList<>();
		this.orderRepository.findAll().iterator().forEachRemaining(orders::add);
		return orders;
	}

	@Override
	public Order getOneById(Long id) throws Exception {
		Optional<Order> order = this.orderRepository.findById(id);
		if(!order.isPresent()) {
			throw new ResourceNotFoundException("There is not order with ID =" + id);			
		}
		return order.get();
	}

	@Transactional
	@Override
	public Order create(Order entity) throws Exception {
		entity.getOrderDetails().forEach(item ->
				item.setOrder(entity));
		return this.orderRepository.save(entity);		
	}

	@Transactional
	@Override
	public Order update(Long id, Order entity) throws Exception {		
		Order currentOrder = this.getOneById(id);
		currentOrder.setDateReception(entity.getDateReception());
		currentOrder.setName(entity.getName());
		currentOrder.setState(entity.getState());
		currentOrder.setOrderDetails(entity.getOrderDetails());
		return this.orderRepository.save(currentOrder);
	}

	@Transactional
	@Override
	public void delete(Long id) throws Exception {
		this.orderRepository.deleteById(id);
	}

	@Override
	public Page<Order> findAll(Pageable pageable) throws Exception {
		return this.orderRepository.findAll(pageable);
	}

	@Override
	public Page<Order> fetchByName(String name, Pageable pageable) throws Exception {
		return this.orderRepository.fetchByName(name, pageable);
	}
}
