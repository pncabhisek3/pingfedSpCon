package com.cisco.pmtpf.server.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SPConnectionController {

	@GetMapping(value = "/createConnection")
	public String get_CreateConnectionPage(Model model) {
		model.addAttribute("href", "createConnection");
		return "connection/addSpConnection";
	}

	@GetMapping(value = "/modifyConnection")
	public String get_ModifyConnectionPage(Model model) {
		model.addAttribute("href", "modifyConnection");
		return "connection/manageSpConnection";
	}

	@GetMapping(value = "/connection")
	public String getConenctions(Model model) {
		model.addAttribute("href", "connection");
		return "connection/addSpConnection";
	}

	@GetMapping(value = "/")
	public String defaultRedirect(Model model) {
		model.addAttribute("href", "createConnection");
		return "redirect:/connection";
	}
	
	@GetMapping(value = "/logout")
	public String logout() {
		System.out.println("Custom logout...");
		return "redirect:/connection";
	}

}
