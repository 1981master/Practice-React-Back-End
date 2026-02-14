package com.master.practiceReact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@EntityScan(basePackages = "com.master.practiceReact.models.Entity")
public class PracticeReactApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracticeReactApplication.class, args);
	}

}
