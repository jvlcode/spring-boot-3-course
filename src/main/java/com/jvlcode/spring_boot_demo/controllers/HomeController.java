package com.jvlcode.spring_boot_demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/home")
public class HomeController {
	@GetMapping
	public String successPage() {
		return "Login Successful!";
	}
}
