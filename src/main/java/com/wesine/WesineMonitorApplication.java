package com.wesine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class WesineMonitorApplication {

	
	
	@RequestMapping("/")
	public String greet(){
		  return "hello spring boot";
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(WesineMonitorApplication.class, args);
	}
}
