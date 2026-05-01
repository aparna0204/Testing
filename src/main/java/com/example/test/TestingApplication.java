package com.example.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestingApplication {

	public static void main(String[] args) {
		  System.out.println("Hello, World!");
		
		SpringApplication.run(TestingApplication.class, args);
	}

}
