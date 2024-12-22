package org.launchcode.moviedock;

import org.launchcode.moviedock.data.UserRepository;
import org.launchcode.moviedock.models.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MoviedockApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviedockApplication.class, args);
	}

}
