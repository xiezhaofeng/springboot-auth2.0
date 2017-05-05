package com.websystique.springmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.websystique.springmvc.security.MethodSecurityConfig;

//@EnableAutoConfiguration
@SpringBootApplication
@EnableResourceServer
//@Configuration
@EnableAuthorizationServer
@EnableWebSecurity
//@EnableOAuth2Client
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class)
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = false)
@Import({MethodSecurityConfig.class})
@EnableWebMvc
@ComponentScan(basePackages = "com.websystique.springmvc")
public class Application
{

	public static void main(String[] args)
	{
		SpringApplication.run(Application.class, args);
	}
}
