package com.volanty;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties
@SpringBootApplication
public class CavApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CavApiApplication.class, args);
	}

	@Bean
	public Gson gson() {
		return new Gson();
	}
}
