package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import com.example.security.OAuth2SecurityConfiguration;

@SpringBootApplication
@EnableResourceServer
@AutoConfigureAfter(OAuth2SecurityConfiguration.class)
public class ResourceServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResourceServerApplication.class, args);
	}
}
