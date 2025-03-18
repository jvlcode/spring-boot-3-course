package com.jvlcode.spring_boot_demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class HomeController {
	
	@GetMapping
	public String getHomePage() {
		return "Welcome To Home Page";
	}
	
	@GetMapping("/dashboard")
	public String getDasboardPage() {
		return "Login Succesfull";
	}
}
