package com.rapifarma.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.processing.RoundEnvironment;
import javax.naming.spi.DirStateFactory.Result;
import javax.persistence.PrePersist;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;

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

import com.rapifarma.common.PageInitPaginationVoucher;
import com.rapifarma.model.entity.Patient;
import com.rapifarma.model.entity.Voucher;
import com.rapifarma.model.entity.VoucherDetail;
import com.rapifarma.service.PatientService;
import com.rapifarma.service.ProductService;
import com.rapifarma.service.VoucherService;

import net.bytebuddy.dynamic.scaffold.MethodRegistry.Handler.ForAbstractMethod;

@Controller
@RequestMapping("/vouchers")
@Secured({"ROLE_ADMIN","ROLE_USER"})
public class VoucherController {
	protected static final String VOUCHER_DETAIL_ADD_FORM_VIEW = "vouchers/vouchersDetails/new";
	protected static final String VOUCHER_ADD_FORM_VIEW = "vouchers/new";
	protected static final String VOUCHER_EDIT_FORM_VIEW = "vouchers/edit";
	protected static final String VOUCHER_PAGE_VIEW = "vouchers/list";
	protected static final String INDEX_VIEW = "index";
	
	private List<VoucherDetail> vouchersDetails = new ArrayList<>();	
	private double subTotal = 0;	
	private double igv = 0;	
	private double total = 0;
	
	@Autowired
	private VoucherService voucherService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private PageInitPaginationVoucher pageInitPaginationVoucher;
	
	public void limpiarVariables() {
		this.vouchersDetails = new ArrayList<>();
		this.subTotal = 0;
		this.igv = 0;
		this.total = 0;
	}
	
	
	@GetMapping
	public ModelAndView getAllVouchers(
			@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page, Model model) throws Exception{
		this.limpiarVariables();
		model.addAttribute("patients", this.patientService.getAll());
		model.addAttribute("patient", new Patient());
		return this.pageInitPaginationVoucher.initPagination(pageSize, page, VOUCHER_PAGE_VIEW);
	}
	
	@GetMapping("/search")
	public ModelAndView getVoucherByName(
			@RequestParam("name") String name,
			@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) throws Exception{
		
		ModelAndView modelAndView;
		
		if(!name.isEmpty()) {
			if(!this.pageInitPaginationVoucher.initPaginationByName(pageSize, page, VOUCHER_PAGE_VIEW, name).isEmpty()) {
				modelAndView = this.pageInitPaginationVoucher.initPaginationByName(pageSize, page, VOUCHER_PAGE_VIEW, name);
			}else {					
				modelAndView = this.pageInitPaginationVoucher.initPagination(pageSize, page, VOUCHER_PAGE_VIEW);
			}
		}else {				
			modelAndView = this.pageInitPaginationVoucher.initPagination(pageSize, page, VOUCHER_PAGE_VIEW);
		}		
		return modelAndView;
	}
	
	
	@GetMapping("/new")
	public String newVoucher(Model model, RedirectAttributes attr) throws Exception{
		if(!model.containsAttribute("voucher")) {
			model.addAttribute("voucher", new Voucher());
			model.addAttribute("voucherDetail", new VoucherDetail());
			model.addAttribute("products", this.productService.getAll());
			model.addAttribute("patients", this.patientService.getAll());
			model.addAttribute("voucherDetailList", this.vouchersDetails);
			model.addAttribute("total", this.total);
			model.addAttribute("subTotal", this.subTotal);
			model.addAttribute("igv", this.igv);
		}	
		return VOUCHER_ADD_FORM_VIEW;
	}
	
	@PostMapping("/create")
	public String createVoucher(@Valid Voucher voucher, BindingResult result,
			RedirectAttributes attr, Model model) throws Exception{
		if(result.hasErrors()) {
			attr.addFlashAttribute("org.springframework.validation.BindingResult.voucher", result);
			attr.addFlashAttribute("voucher", voucher);			
			return "redirect:/vouchers/new";
		}
		voucher.setVoucherDetails(this.vouchersDetails);
		voucher.setTotal_import(this.total);
		voucher.setIgv(this.igv);
		voucher.setSale_value(this.subTotal);
		this.voucherService.create(voucher);
		return "redirect:/vouchers/";
	}	
		
	@PostMapping("/voucherDetail/create")
	public String createVoucherDetail(@Valid VoucherDetail voucherDetail, Voucher voucher, BindingResult result,
			RedirectAttributes attr, Model model) throws Exception{
		DecimalFormat df = new DecimalFormat("#.00");
		if(result.hasErrors()) {
			attr.addFlashAttribute("org.springframework.validation.BindingResult.voucherDetail", result);
			attr.addFlashAttribute("voucherDetail", voucherDetail);			
			return "redirect:/vouchers/new";
		}else {
			this.vouchersDetails.add(voucherDetail);
			this.total = this.total + (voucherDetail.getQuantity()*voucherDetail.getProduct().getPrice());			
			this.subTotal = Double.parseDouble(df.format(this.total/1.18));			
			this.igv = Double.parseDouble(df.format(this.total - this.subTotal));
		}				
		return "redirect:/vouchers/new";
	}	
}
