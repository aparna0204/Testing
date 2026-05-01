package com.example.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestingApplication {

	public static void main(String[] args) {
		  System.out.println("Hello World! This is a test10 application.");
		  
		  System.out.println("Testing GitHub API integration...over the call testing");
		SpringApplication.run(TestingApplication.class, args);
	}

}
