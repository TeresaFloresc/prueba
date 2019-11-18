package com.rapifarma.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rapifarma.common.PageInitPaginationCategory;
import com.rapifarma.model.entity.Category;
import com.rapifarma.service.CategoryService;

@Controller
@RequestMapping("/categories")
@Secured({"ROLE_ADMIN","ROLE_USER"})
public class CategoryController {
		
	protected static final String CATEGORY_EDIT_FORM_VIEW = "categories/edit";
	protected static final String CATEGORY_ADD_FORM_VIEW = "categories/new";
	protected static final String CATEGORY_PAGE_VIEW = "categories/list";
	protected static final String INDEX_VIEW = "index";
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private PageInitPaginationCategory pageInitPaginationCategory;
	
	@GetMapping
	public ModelAndView getAllCategories(
			@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) throws Exception{
		return this.pageInitPaginationCategory.initPagination(pageSize, page, CATEGORY_PAGE_VIEW);
	}
	
	@GetMapping("/search")
	public ModelAndView getCategoryByName(
			@RequestParam("name") String name,
			@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) throws Exception{
		
		ModelAndView modelAndView;
		
		if(!name.isEmpty()) {
			if(!this.pageInitPaginationCategory.initPaginationByName(pageSize, page, CATEGORY_PAGE_VIEW, name).isEmpty()) {
				modelAndView = this.pageInitPaginationCategory.initPaginationByName(pageSize, page, CATEGORY_PAGE_VIEW, name);
			}else {					
				modelAndView = this.pageInitPaginationCategory.initPagination(pageSize, page, CATEGORY_PAGE_VIEW);
			}
		}else {				
			modelAndView = this.pageInitPaginationCategory.initPagination(pageSize, page, CATEGORY_PAGE_VIEW);
		}		
		return modelAndView;
	}
	
	@GetMapping("/new")
	public String newCategory(Model model) {		
		if(!model.containsAttribute("category")) {
			model.addAttribute("category", new Category());
		}		
		return CATEGORY_ADD_FORM_VIEW;
	}
	
	@GetMapping("{id}/edit")
	public String editCategory(@PathVariable(value = "id") Long categoryId,
			Model model) throws Exception{
		if(!model.containsAttribute("category")) {
			model.addAttribute("category", this.categoryService.getOneById(categoryId));
		}		
		return CATEGORY_EDIT_FORM_VIEW;
	}
	
	@PostMapping("/create")
	public String createCategory(@Valid Category category, BindingResult result, 
			RedirectAttributes attr, Model model, SessionStatus status) throws Exception{
		if(result.hasErrors()) {
			attr.addFlashAttribute("org.springframework.validation.BindingResult.category", result);
			attr.addFlashAttribute("category", category);
			return "redirect:/categories/new/";
		}		
		this.categoryService.create(category);		
		attr.addFlashAttribute("success", "Categoria registrada correctamente");
		return "redirect:/categories/";
	}
	
	
	@PostMapping(path = "/{id}/update")
	public String updateCategory(@PathVariable(value = "id") Long categoryId, @Valid Category category, 
			BindingResult result, RedirectAttributes attr, Model model) throws Exception{
		if(result.hasErrors()) {
			attr.addFlashAttribute("org.springframework.validation.BindingResult.category", result);
			attr.addFlashAttribute("category", category);
			return "redirect:/categories/"+category.getId()+"/edit";
		}
		this.categoryService.update(categoryId, category);
		model.addAttribute("category", this.categoryService.getOneById(categoryId));
		attr.addFlashAttribute("success", "Categoria editada correctamente");
		return "redirect:/categories/";
	}
	
}
