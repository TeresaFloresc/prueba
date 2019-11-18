package com.rapifarma.common;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.rapifarma.model.entity.Category;
import com.rapifarma.service.CategoryService;

@Component
public class PageInitPaginationCategory {

	@Autowired
	private CategoryService categoryService;

	// pagination
	private static final int BUTTONS_TO_SHOW = 3;
	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 5;
	private static final int[] PAGE_SIZES = { 5, 10 };

	public  ModelAndView initPagination(Optional<Integer> pageSize, Optional<Integer> page, 
			String url) throws Exception{
		ModelAndView initModelView = new ModelAndView(url);
		
		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		Page<Category> categoryList = this.categoryService.findAll(PageRequest.of(evalPage, evalPageSize));
		PagerModel pager = new PagerModel(categoryList.getTotalPages(), categoryList.getNumber(), BUTTONS_TO_SHOW);

		initModelView.addObject("categoriesList", categoryList);
		initModelView.addObject("selectedPageSize", evalPageSize);
		initModelView.addObject("pageSizes", PAGE_SIZES);
		initModelView.addObject("pager", pager);
	
		return initModelView;
	}
	
	public  ModelAndView initPaginationByName(Optional<Integer> pageSize, Optional<Integer> page, 
			String url, String name) throws Exception{
		ModelAndView initModelView = new ModelAndView(url);
		
		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		Page<Category> categoryListByName = this.categoryService.fetchByName(name,PageRequest.of(evalPage, evalPageSize));
		PagerModel pager = new PagerModel(categoryListByName.getTotalPages(), categoryListByName.getNumber(), BUTTONS_TO_SHOW);

		initModelView.addObject("categoriesList", categoryListByName);
		initModelView.addObject("selectedPageSize", evalPageSize);
		initModelView.addObject("pageSizes", PAGE_SIZES);
		initModelView.addObject("pager", pager);
			 	
		return initModelView;
	}

}
