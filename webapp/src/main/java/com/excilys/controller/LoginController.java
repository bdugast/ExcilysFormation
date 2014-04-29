package com.excilys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController
{
	@RequestMapping(method = RequestMethod.GET)
	public String doGet(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			ModelMap map) {
		
	 	if (error != null) {
			map.addAttribute("error", "Invalid username and password!");
		}
	 
	 	if (logout != null) {
	 		map.addAttribute("msg", "You've been logged out successfully.");
	 	}
	 	
		return "login";
	}
}