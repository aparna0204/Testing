package com.example.test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class TestingApplication {
	public static void main(String[] args) {
		String s = null;
		String password = "12345678897234gh9843278687134546789687685gjhfsgjvnvn";
		if( s.length() > 0) {
		  System.out.println("Hello World! This is a test12 application."+ s.length());
		  System.out.println("Testing the application over here on 0406-10"+ s.length());
		  
		}
		  
		  System.out.println("Testing GitHub API integration...over the call testing");
		  
		  
		SpringApplication.run(TestingApplication.class, args);
	}

}
