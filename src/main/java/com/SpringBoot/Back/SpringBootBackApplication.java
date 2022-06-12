package com.SpringBoot.Back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringBootBackApplication implements CommandLineRunner  {

	// CommandLineRunner -> ejecutar algo antes de iniciar nuestra aplicaión
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBackApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		String password = "12345";

		for (int i = 0; i < 4; i++) {
			String passwordBcrypt = this.passwordEncoder.encode(password);
			System.out.println(passwordBcrypt);
		}
		
	}

}
