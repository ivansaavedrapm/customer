package com.customer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello-world")
public class CtrlHelloWorld {

	@GetMapping
	public String HelloWorld() {
		return "Hola Mundo :(";
	}
}
