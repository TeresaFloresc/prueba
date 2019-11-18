package com.rapifarma.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/","/home"})
public class HomeController {
	
	protected static final String INDEX_VIEW = "index"; 
	
	@GetMapping
	public ModelAndView getIndex(@RequestParam(value = "logout", required = false) String logout,
			Model model,  Principal principal) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(INDEX_VIEW);
		
		if (principal != null) {
			model.addAttribute("success", "Bienvenido a Rapifarma: " + principal.getName());			
		}
		
		if (logout != null) {
			model.addAttribute("success", "Ha cerrado sesión con éxito!");			
		}
		return modelAndView;
	}
}