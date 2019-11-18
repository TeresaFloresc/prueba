package com.rapifarma.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
public class LoginController {

	@GetMapping(value = { "/login" })
	public String login(@RequestParam(value = "error", required = false) String error,
			Model model, Principal principal,RedirectAttributes flash) {

		if (principal != null) {
			model.addAttribute("success", "Bienvenido");
			return "redirect:/home";
		}

		if (error != null) {
			model.addAttribute("error",
					"Error en el login: Nombre de usuario o contraseña incorrecta, por favor vuelva a intentarlo!");			
		}
		return "login";
	}
}