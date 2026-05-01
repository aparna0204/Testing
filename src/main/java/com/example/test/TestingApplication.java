package com.example.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestingApplication {

	public static void main(String[] args) {
		  System.out.println("Hello World! This is a test2 application.");
		  
		  System.out.println("Testing GitHub API integration...");
		SpringApplication.run(TestingApplication.class, args);
	}

}
