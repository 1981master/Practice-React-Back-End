package com.master.practiceReact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.master.practiceReact.models.Entity")
public class PracticeReactApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracticeReactApplication.class, args);
	}

}
