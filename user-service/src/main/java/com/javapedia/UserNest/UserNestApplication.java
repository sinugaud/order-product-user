package com.javapedia.UserNest;

import com.javapedia.UserNest.model.Role;
import com.javapedia.UserNest.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class UserNestApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserNestApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(RoleRepository roleRepo) {
		return (args) -> {
			com.javapedia.UserNest.model.Role role = new Role();
			role.setName("ROLE_ADMIN");
			roleRepo.save(role);
		};
	}
}
