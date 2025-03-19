package com.shyam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CronExpressionApplication {

	public static void main(String[] args) {
		SpringApplication.run(CronExpressionApplication.class, args);
	}

}
