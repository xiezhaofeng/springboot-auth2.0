package com.example.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@Order(1)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
	
	private static final String RESOURCE_ID = "resourceServer";
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources
			.resourceId(RESOURCE_ID)
			.stateless(false);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		//.anonymous().disable()
		//.requestMatchers().antMatchers("/user*//**")
		//.and()
		.requestMatchers()
		.antMatchers("/user/**")
		.and()
		.authorizeRequests().anyRequest()//.access("#oauth2.hasScope('read')");
		.hasAnyRole("ADMIN");
//		.and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
		
 }