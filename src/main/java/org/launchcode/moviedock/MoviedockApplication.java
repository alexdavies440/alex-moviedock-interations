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
//
//	@Bean
//	CommandLineRunner commandLineRunner(UserRepository users, PasswordEncoder encoder) {
//		return args -> {
//			users.save(new User("user", "user@user.com", encoder.encode("password"), "ROLE_USER"));
//			users.save(new User("admin", "admin@admin.com", encoder.encode("password"), "ROLE_USER,ROLE_ADMIN"));
//			users.save(new User("aldo", "aldo@aldo.com", encoder.encode("password"), "ROLE_USER"));
//		};
//	}
}
