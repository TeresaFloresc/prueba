package com.rapifarma.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rapifarma.common.PageInitPaginationOrder;
import com.rapifarma.model.entity.Order;
import com.rapifarma.model.entity.OrderDetail;
import com.rapifarma.service.OrderService;
import com.rapifarma.service.ProductService;

@Controller
@RequestMapping("/orders")
@Secured({"ROLE_ADMIN","ROLE_USER"})
public class OrderController {

	protected static final String ORDER_DETAIL_ADD_FORM_VIEW = "orders/ordersDetails/new";
	protected static final String ORDER_ADD_FORM_VIEW = "orders/new";
	protected static final String ORDER_EDIT_FORM_VIEW = "orders/edit";
	protected static final String ORDER_PAGE_VIEW = "orders/list";
	protected static final String INDEX_VIEW = "index";
	
	private List<OrderDetail> ordersDetails = new ArrayList<>();			
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private PageInitPaginationOrder pageInitPaginationOrder;
	
	@GetMapping
	public ModelAndView getAllOrders(
			@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) throws Exception{
		this.ordersDetails = new ArrayList<>();
		return this.pageInitPaginationOrder.initPagination(pageSize, page, ORDER_PAGE_VIEW);
	}
	
	@GetMapping("/search")
	public ModelAndView getOrderByName(
			@RequestParam("name") String name,
			@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) throws Exception{
		
		ModelAndView modelAndView;
		
		if(!name.isEmpty()) {
			if(!this.pageInitPaginationOrder.initPaginationByName(pageSize, page, ORDER_PAGE_VIEW, name).isEmpty()) {
				modelAndView = this.pageInitPaginationOrder.initPaginationByName(pageSize, page, ORDER_PAGE_VIEW, name);
			}else {					
				modelAndView = this.pageInitPaginationOrder.initPagination(pageSize, page, ORDER_PAGE_VIEW);
			}
		}else {				
			modelAndView = this.pageInitPaginationOrder.initPagination(pageSize, page, ORDER_PAGE_VIEW);
		}		
		return modelAndView;
	}
	
	
	@GetMapping("/new")
	public String newOrder(Model model, RedirectAttributes attr) throws Exception{
		if(!model.containsAttribute("order")) {
			model.addAttribute("order", new Order());
			model.addAttribute("orderDetail", new OrderDetail());
			model.addAttribute("products", this.productService.getAll());	
			model.addAttribute("orderDetailList", this.ordersDetails);
		}	
		return ORDER_ADD_FORM_VIEW;
	}
	
	@PostMapping("/create")
	public String createOrder(@Valid Order order, BindingResult result,
			RedirectAttributes attr, Model model) throws Exception{
		if(result.hasErrors()) {
			attr.addFlashAttribute("org.springframework.validation.BindingResult.order", result);
			attr.addFlashAttribute("order", order);			
			return "redirect:/orders/new";
		}
		order.setOrderDetails(this.ordersDetails);				
		this.orderService.create(order);
		return "redirect:/orders/";
	}	
		
	@PostMapping("/orderDetail/create")
	public String createOrderDetail(@Valid OrderDetail orderDetail, Order order, BindingResult result,
			RedirectAttributes attr, Model model) throws Exception{
		if(result.hasErrors()) {
			attr.addFlashAttribute("org.springframework.validation.BindingResult.orderDetail", result);
			attr.addFlashAttribute("orderDetail", orderDetail);			
			return "redirect:/orders/new";
		}else {
			this.ordersDetails.add(orderDetail);			
		}				
		return "redirect:/orders/new";
	}	
}
