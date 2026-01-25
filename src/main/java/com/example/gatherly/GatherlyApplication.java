package com.example.gatherly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GatherlyApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatherlyApplication.class, args);
	}

}
