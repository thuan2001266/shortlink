package com.nauht.shortlink;

import com.nauht.shortlink.auth.AuthenticationRequest;
import com.nauht.shortlink.auth.AuthenticationService;
import com.nauht.shortlink.auth.RegisterRequest;
import com.nauht.shortlink.linkmap.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.nauht.shortlink.user.Role.ADMIN;
import static com.nauht.shortlink.user.Role.MANAGER;
@SpringBootApplication
public class ShortlinkApplication {
	@Autowired
	LinkRepository linkRepository;

	public static void main(String[] args) {
		SpringApplication.run(ShortlinkApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service
	) {
		return args -> {
			try {
			} catch (Exception e){
			}
		};
	}



}
