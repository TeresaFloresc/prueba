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

import com.rapifarma.common.PageInitPaginationPatient;
import com.rapifarma.model.entity.Patient;
import com.rapifarma.service.PatientService;

@Controller
@RequestMapping("/patients")
@Secured({"ROLE_ADMIN","ROLE_USER"})
public class PatientController {
	
	protected static final String PATIENT_EDIT_FORM_VIEW = "patients/edit";
	protected static final String PATIENT_ADD_FORM_VIEW = "patients/new";
	protected static final String PATIENT_PAGE_VIEW = "patients/list";
	protected static final String INDEX_VIEW = "index";
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private PageInitPaginationPatient pageInitPaginationPatient;
	
	@GetMapping
	public ModelAndView getAllPatients(
			@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) throws Exception{			
		return this.pageInitPaginationPatient.initPagination(pageSize, page, PATIENT_PAGE_VIEW);
	}
	
	@GetMapping("/search")
	public ModelAndView getPatientByName(
			@RequestParam("name") String name,
			@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) throws Exception{
		
		ModelAndView modelAndView;
		
		if(!name.isEmpty()) {
			if(!this.pageInitPaginationPatient.initPaginationByName(pageSize, page, PATIENT_PAGE_VIEW, name).isEmpty()) {
				modelAndView = this.pageInitPaginationPatient.initPaginationByName(pageSize, page, PATIENT_PAGE_VIEW, name);
			}else {					
				modelAndView = this.pageInitPaginationPatient.initPagination(pageSize, page, PATIENT_PAGE_VIEW);
			}
		}else {				
			modelAndView = this.pageInitPaginationPatient.initPagination(pageSize, page, PATIENT_PAGE_VIEW);
		}		
		return modelAndView;
	}
	
	@GetMapping("/new")
	public String newPatient(Model model) {		
		if(!model.containsAttribute("patient")) {
			model.addAttribute("patient", new Patient());
		}		
		return PATIENT_ADD_FORM_VIEW;
	}
	
	@GetMapping("{id}/edit")
	public String editPatient(@PathVariable(value = "id") Long patientId,
			Model model) throws Exception{
		if(!model.containsAttribute("patient")) {
			model.addAttribute("patient", this.patientService.getOneById(patientId));
		}		
		return PATIENT_EDIT_FORM_VIEW;
	}
	
	@PostMapping("/create")
	public String createPatient(@Valid Patient patient, BindingResult result, 
			RedirectAttributes attr, Model model, SessionStatus status) throws Exception{
		if(result.hasErrors()) {
			attr.addFlashAttribute("org.springframework.validation.BindingResult.patient", result);
			attr.addFlashAttribute("patient", patient);
			return "redirect:/patients/new/";
		}		
		this.patientService.create(patient);		
		attr.addFlashAttribute("success", "Paciente registrado correctamente");
		return "redirect:/patients/";
	}
	
	@PostMapping(path = "/{id}/update")
	public String updatePatient(@PathVariable(value = "id") Long patientId, @Valid Patient patient, 
			BindingResult result, RedirectAttributes attr, Model model) throws Exception{
		if(result.hasErrors()) {
			attr.addFlashAttribute("org.springframework.validation.BindingResult.patient", result);
			attr.addFlashAttribute("patient", patient);
			return "redirect:/patients/"+patient.getId()+"/edit";
		}
		this.patientService.update(patientId, patient);
		model.addAttribute("patient", this.patientService.getOneById(patientId));
		attr.addFlashAttribute("success", "Paciente editado correctamente");
		return "redirect:/patients/";
	}
	
}
