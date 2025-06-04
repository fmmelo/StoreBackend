package com.myproject.myproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class MyprojectApplication {

	public static void main(String[] args) {
		System.out.println("Hey from Spring inside Docker!");
		SpringApplication.run(MyprojectApplication.class, args);
	}

}
