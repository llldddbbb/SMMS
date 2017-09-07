package com.smms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:/smms.properties")
public class SmmsApplication  extends SpringBootServletInitializer {

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(SmmsApplication.class);
//	}

	public static void main(String[] args) {
		SpringApplication.run(SmmsApplication.class, args);
	}
}
