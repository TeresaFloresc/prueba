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
import com.rapifarma.model.entity.Product;
import com.rapifarma.model.repository.ProductRepository;
import com.rapifarma.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepository;
	
	@Override	
	public List<Product> getAll() throws Exception {
		List<Product> products = new ArrayList<>();
		this.productRepository.findAll().iterator().forEachRemaining(products::add);
		return products;
	}
	
	@Override
	public Product getOneById(Long id) throws Exception {
		Optional<Product> product = this.productRepository.findById(id);
		if(!product.isPresent()) {
			throw new ResourceNotFoundException("There is no Product with ID = " + id);
		}
		return product.get();
	}

	@Override
	@Transactional
	public Product create(Product entity) throws Exception {
		return this.productRepository.save(entity);	
	}

	@Override
	@Transactional
	public Product update(Long id, Product entity) throws Exception {
		Product currentProduct = this.getOneById(id);
		currentProduct.setName(entity.getName());
		currentProduct.setLocation(entity.getLocation());
		currentProduct.setMin_stock(entity.getMin_stock());
		currentProduct.setPrice(entity.getPrice());
		currentProduct.setStock(entity.getStock());
		currentProduct.setUnit(entity.getUnit());
		currentProduct.setSupplier(entity.getSupplier());
		currentProduct.setCategories(entity.getCategories());
		return this.productRepository.save(currentProduct);
	}

	@Override
	@Transactional
	public void delete(Long id) throws Exception {
		this.productRepository.deleteById(id);		
	}

	@Override
	public Page<Product> findAll(Pageable pageable) throws Exception {
		return this.productRepository.findAll(pageable);
	}

	@Override
	public Page<Product> fetchByName(String name, Pageable pageable) throws Exception {
		return this.productRepository.fetchByName(name, pageable);
	}
}
