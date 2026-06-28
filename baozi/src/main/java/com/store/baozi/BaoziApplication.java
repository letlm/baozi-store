package com.store.baozi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.store.baozi.model")
@EnableJpaRepositories(basePackages = "com.store.baozi.repository")
public class BaoziApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaoziApplication.class, args);
	}

}
