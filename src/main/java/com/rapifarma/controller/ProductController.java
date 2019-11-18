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

import com.rapifarma.common.PageInitPaginationProduct;
import com.rapifarma.model.entity.Product;
import com.rapifarma.service.CategoryService;
import com.rapifarma.service.ProductService;
import com.rapifarma.service.SupplierService;

@Controller
@RequestMapping("/products")
@Secured({"ROLE_ADMIN","ROLE_USER"})
public class ProductController {
	
	protected static final String PRODUCT_EDIT_FORM_VIEW = "products/edit";
	protected static final String PRODUCT_ADD_FORM_VIEW = "products/new";
	protected static final String PRODUCT_PAGE_VIEW = "products/list";
	protected static final String PRODUCT_VIEW = "index";
	
	@Autowired
	private ProductService productService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private PageInitPaginationProduct pageInitPaginationProduct;
	
	@GetMapping
	public ModelAndView getAllProducts(
			@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) throws Exception{		
		return this.pageInitPaginationProduct.initPagination(pageSize, page, PRODUCT_PAGE_VIEW);
	}
		
	@GetMapping("/search")
	public ModelAndView getProductByName(
			@RequestParam("name") String name,
			@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) throws Exception{
		
		ModelAndView modelAndView;		
		
		if(!name.isEmpty()) {
			if(!this.pageInitPaginationProduct.initPaginationByName(pageSize, page, PRODUCT_PAGE_VIEW, name).isEmpty()) {
				modelAndView = this.pageInitPaginationProduct.initPaginationByName(pageSize, page, PRODUCT_PAGE_VIEW, name);
			}else {					
				modelAndView = this.pageInitPaginationProduct.initPagination(pageSize, page, PRODUCT_PAGE_VIEW);
			}
		}else {				
			modelAndView = this.pageInitPaginationProduct.initPagination(pageSize, page, PRODUCT_PAGE_VIEW);
		}		
		return modelAndView;
	}
	
	@GetMapping("/new")
	public String newProduct(Model model) throws Exception{
		if(!model.containsAttribute("product")) {
			model.addAttribute("product", new Product());
			model.addAttribute("categories", this.categoryService.getAll());
			model.addAttribute("suppliers", this.supplierService.getAll());
		}		
		return PRODUCT_ADD_FORM_VIEW;
	}
	
	@GetMapping("{id}/edit")
	public String editProduct(@PathVariable(value = "id") Long productId, Model model) throws Exception{
		if(!model.containsAttribute("product")) {
			model.addAttribute("product", this.productService.getOneById(productId));
			model.addAttribute("categories", this.categoryService.getAll());
			model.addAttribute("suppliers", this.supplierService.getAll());	
		}		
		return PRODUCT_EDIT_FORM_VIEW;
	}
	
	@PostMapping("/create")
	public String createProduct(@Valid Product product, BindingResult result, 
			RedirectAttributes attr, Model model) throws Exception{		
		if(result.hasErrors()) {
			attr.addFlashAttribute("org.springframework.validation.BindingResult.product", result);
			attr.addFlashAttribute("product", product);
			attr.addFlashAttribute("categories", this.categoryService.getAll());
			attr.addFlashAttribute("suppliers", this.supplierService.getAll());
			return "redirect:/products/new/";
		}		
		this.productService.create(product);
		attr.addFlashAttribute("success", "Producto registrado correctamente");
		return "redirect:/products/";
	}
	
	@PostMapping(path = "/{id}/update")
	public String updateProduct(@PathVariable(value = "id") Long productId, @Valid Product product, 
			BindingResult result, RedirectAttributes attr, Model model) throws Exception{
		if(result.hasErrors()) {
			attr.addFlashAttribute("org.springframework.validation.BindingResult.product", result);
			attr.addFlashAttribute("product", product);
			attr.addFlashAttribute("categories", this.categoryService.getAll());
			attr.addFlashAttribute("suppliers", this.supplierService.getAll());
			return "redirect:/products/"+product.getId()+"/edit";
		}
		this.productService.update(productId, product);
		model.addAttribute("product", this.productService.getOneById(productId));
		attr.addFlashAttribute("success", "Producto editado correctamente");
		return "redirect:/products/";
	}
}
