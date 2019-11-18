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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rapifarma.common.PageInitPaginationSupplier;
import com.rapifarma.model.entity.Supplier;
import com.rapifarma.service.SupplierService;

@Controller
@RequestMapping("/suppliers")
@Secured({"ROLE_ADMIN","ROLE_USER"})
public class SupplierController {
	
	protected static final String SUPPLIER_ADD_FORM_VIEW = "suppliers/new";	
	protected static final String SUPPLIER_EDIT_FORM_VIEW = "suppliers/edit";
	protected static final String SUPPLIER_PAGE_VIEW = "suppliers/list";
	protected static final String SUPPLIER_VIEW = "index";

	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private PageInitPaginationSupplier pageInitPaginationSupplier;
	
	@GetMapping
	public ModelAndView getAllSuppliers (
			@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) throws Exception{
		return this.pageInitPaginationSupplier.initPagination(pageSize, page, SUPPLIER_PAGE_VIEW);
	}
	
	@GetMapping("/search")
	public ModelAndView getSupplierByName(
			@RequestParam("name") String name,
			@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) throws Exception{
		
		ModelAndView modelAndView;
		
		if(!name.isEmpty()) {
			if(!this.pageInitPaginationSupplier.initPaginationByName(pageSize, page, SUPPLIER_PAGE_VIEW, name).isEmpty()) {
				modelAndView = this.pageInitPaginationSupplier.initPaginationByName(pageSize, page, SUPPLIER_PAGE_VIEW, name);
			}else {					
				modelAndView = this.pageInitPaginationSupplier.initPagination(pageSize, page, SUPPLIER_PAGE_VIEW);
			}
		}else {				
			modelAndView = this.pageInitPaginationSupplier.initPagination(pageSize, page, SUPPLIER_PAGE_VIEW);
		}		
		return modelAndView;
	}
	
	@GetMapping("/new")
	public String newSupplier(Model model) {
		if(!model.containsAttribute("supplier")){
			model.addAttribute("supplier", new Supplier());
		}		
		return SUPPLIER_ADD_FORM_VIEW;
	}
	
	@GetMapping("{id}/edit")
	public String editSupplier(@PathVariable(value = "id") Long supplierId, Model model) throws Exception{
		if(!model.containsAttribute("supplier")) {
			model.addAttribute("supplier", this.supplierService.getOneById(supplierId));
		}		
		return SUPPLIER_EDIT_FORM_VIEW;
	}	
	
	@PostMapping("/create")
	public String createSupplier(@Valid Supplier supplier, BindingResult result, 
			RedirectAttributes attr, Model model) throws Exception{				
		if(result.hasErrors()) {
			attr.addFlashAttribute("org.springframework.validation.BindingResult.supplier", result);
			attr.addFlashAttribute("supplier", supplier);
			return "redirect:/suppliers/new";
		}
		
		this.supplierService.create(supplier);
		attr.addFlashAttribute("success", "Proveedor registrado correctamente");
		return "redirect:/suppliers/";
	}
	
	@PostMapping(path = "/{id}/update")
	public String updateSupplier(@PathVariable(value = "id") Long supplierId, @Valid Supplier supplier, 
			BindingResult result, RedirectAttributes attr, Model model) throws Exception{
		if(result.hasErrors()) {
			attr.addFlashAttribute("org.springframework.validation.BindingResult.supplier", result);
			attr.addFlashAttribute("supplier", supplier);
			return "redirect:/suppliers/"+supplier.getId()+"/edit";
		}
		this.supplierService.update(supplierId, supplier);
		model.addAttribute("supplier", this.supplierService.getOneById(supplierId));
		attr.addFlashAttribute("success", "Proveedor editado correctamente");
		return "redirect:/suppliers/";
	}
}
