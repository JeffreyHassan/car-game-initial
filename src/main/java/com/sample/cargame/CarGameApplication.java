package com.sample.cargame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CarGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarGameApplication.class, args);
	}

}
