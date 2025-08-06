package com.hackathon.ibm;

import com.hackathon.ibm.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RiskAssessmentApplication {

	@Autowired UserService userService;
	@PostConstruct
	public void init() {
		if (!userService.existsByEmail("admin@example.com")) {
			userService.createAdmin("Admin", "admin@example.com", "admin_password");
		}
	}

	public static void main(String[] args) {

		SpringApplication.run(RiskAssessmentApplication.class, args);
	}

}
