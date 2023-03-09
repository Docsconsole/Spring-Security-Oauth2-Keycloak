package com.docsconsole.tutorials;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringOauth2KeycloakApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringOauth2KeycloakApp.class, args);
	}
	@Bean public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
