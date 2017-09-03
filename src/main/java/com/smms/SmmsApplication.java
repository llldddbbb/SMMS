package com.smms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:/smms.properties")
public class SmmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmmsApplication.class, args);
	}
}
