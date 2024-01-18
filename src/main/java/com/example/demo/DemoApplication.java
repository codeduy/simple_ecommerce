package com.example.demo;

import com.example.demo.models.Role;
import com.example.demo.repositories.RoleRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	InitializingBean sendDatabase(RoleRepository roleRepository) {
		return () -> {
			if (roleRepository.count() <= 0) {
				roleRepository.save(Role.builder().name("USER").build());
				roleRepository.save(Role.builder().name("ADMIN").build());
			}
		};
	}
}
