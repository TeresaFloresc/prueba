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
import com.rapifarma.model.entity.Category;
import com.rapifarma.model.repository.CategoryRepository;
import com.rapifarma.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public List<Category> getAll() throws Exception {
		List<Category> categories = new ArrayList<>();
		this.categoryRepository.findAll().iterator().forEachRemaining(categories::add);
		return categories;
	}
	
	
	@Override
	public Category getOneById(Long id) throws Exception {
		Optional<Category> category = this.categoryRepository.findById(id);
		if(!category.isPresent()) {
			throw new ResourceNotFoundException("There is no Category with ID = " + id);
		}
		return category.get();
	}

	@Transactional
	@Override
	public Category create(Category entity) throws Exception {		
		return this.categoryRepository.save(entity);
	}

	@Transactional
	@Override
	public Category update(Long id, Category entity) throws Exception {
		Category currentCategory = this.getOneById(id);
		currentCategory.setName(entity.getName());
		return this.categoryRepository.save(currentCategory);
	}

	@Transactional
	@Override
	public void delete(Long id) throws Exception {
		this.categoryRepository.deleteById(id);
	}

	@Override
	public Page<Category> findAll(Pageable pageable) {
		return this.categoryRepository.findAll(pageable);	
	}

	@Override
	public Page<Category> fetchByName(String name, Pageable pageable) throws Exception {
		return this.categoryRepository.fetchByName(name, pageable);
	}

}
