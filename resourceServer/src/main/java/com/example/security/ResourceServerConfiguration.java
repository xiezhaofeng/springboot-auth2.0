package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableWebSecurity
@EnableResourceServer
@AutoConfigureAfter(OAuth2SecurityConfiguration.class)
@Order(6)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
	
	private static final String RESOURCE_ID = "resourceServer";
	private @Autowired TokenStore tokenStore;
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources
			.resourceId(RESOURCE_ID)
			.stateless(false)
			.tokenStore(tokenStore);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
		//.anonymous().disable()
		//.requestMatchers().antMatchers("/user/**")
		//.and()
		.requestMatchers()
		.antMatchers("/user/**")
		.and()
		.authorizeRequests().anyRequest()//.access("#oauth2.hasScope('read')");
		.hasAnyRole("ADMIN");
//		.and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}

	
 }