package com.javapedia;

import com.javapedia.config.RsaKeyConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyConfigProperties.class)
@SpringBootApplication
public class UserServiceJwtApplication {

	public static void main(String[] args) {

		SpringApplication.run(UserServiceJwtApplication.class, args);
	}

}
