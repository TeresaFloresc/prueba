package com.rapifarma.common;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.rapifarma.model.entity.Product;
import com.rapifarma.service.ProductService;

@Component
public class PageInitPaginationProduct {

	@Autowired
	private ProductService productService;
	
	private static final int BUTTONS_TO_SHOW = 3;
	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 5;
	private static final int[] PAGE_SIZES = { 5, 10 };
	
	public  ModelAndView initPagination(Optional<Integer> pageSize, Optional<Integer> page, 
			String url) throws Exception{
		ModelAndView initModelView = new ModelAndView(url); 
		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		Page<Product> productList = this.productService.findAll(PageRequest.of(evalPage, evalPageSize));
		PagerModel pager = new PagerModel(productList.getTotalPages(), productList.getNumber(), BUTTONS_TO_SHOW);

		initModelView.addObject("productsList", productList);
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

		Page<Product> productListByName = this.productService.fetchByName(name,PageRequest.of(evalPage, evalPageSize));
		PagerModel pager = new PagerModel(productListByName.getTotalPages(), productListByName.getNumber(), BUTTONS_TO_SHOW);

		initModelView.addObject("productsList", productListByName);
		initModelView.addObject("selectedPageSize", evalPageSize);
		initModelView.addObject("pageSizes", PAGE_SIZES);
		initModelView.addObject("pager", pager);
		return initModelView;
	}
}
