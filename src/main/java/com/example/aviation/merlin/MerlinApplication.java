package com.example.aviation.merlin;

import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.amazonaws.xray.javax.servlet.AWSXRayServletFilter;

@SpringBootApplication
public class MerlinApplication {

	final Logger logger = LoggerFactory.getLogger(MerlinApplication.class);

	@Bean
	public Filter XrayTracingFilter() {
		return new AWSXRayServletFilter("merlin");
	}

	public static void main(String[] args) {
		// This is easier than fiddling with .security files in the image
		// As ACCP requires a positive action anyways, just make this a part of the process.
		com.amazon.corretto.crypto.provider.AmazonCorrettoCryptoProvider.install();
		SpringApplication.run(MerlinApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			final String[] beanNames = ctx.getBeanDefinitionNames();
			logger.info("Using {} bean(s) provided by Spring Boot", beanNames.length);
		};
	}

}
