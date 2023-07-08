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
//				var admin = RegisterRequest.builder()
//						.firstname("Admin")
//						.lastname("Admin")
//						.email("admin2@mail.com")
//						.password("password")
//						.role(ADMIN)
//						.build();
//				System.out.println("Admin token: " + service.register(admin).getAccessToken());
//				var manager = RegisterRequest.builder()
//						.email("user@mail.com")
//						.password("123123")
//						.role(MANAGER)
//						.build();
//				System.out.println("Manager token: " + service.register(manager).getAccessToken());

				var adminLogin = AuthenticationRequest.builder()
						.email("admin2@mail.com")
						.password("password")
						.build();
				System.out.println("Admin token: " + service.authenticate(adminLogin).getAccessToken());

				var managerLogin = AuthenticationRequest.builder()
						.email("manager@mail.com")
						.password("password")
						.build();
				System.out.println("Manager token: " + service.authenticate(managerLogin).getAccessToken());


			} catch (Exception e){

			}

		};
	}



}
