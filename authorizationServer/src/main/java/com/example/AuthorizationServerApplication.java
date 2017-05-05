package com.example;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.MyUserDetailsService;

@SpringBootApplication
@RestController
@EnableOAuth2Client
@EnableAuthorizationServer
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class AuthorizationServerApplication extends WebSecurityConfigurerAdapter{

	private @Autowired MyUserDetailsService userDetailsService;
	private @Autowired ClientDetailsService clientDetailsService;
	
	
	public static void main(String[] args) {
		SpringApplication.run(AuthorizationServerApplication.class, args);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  http.antMatcher("/**")
	    .authorizeRequests()
	      .antMatchers("/", "/login**", "/webjars/**").permitAll()
	      .anyRequest().authenticated()
	    .and().exceptionHandling()
	    .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/me"));
//	      .accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(userDetailsService);
//		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
	}
	
	
	@RequestMapping({ "/user", "/me" })
	public Map<String, String> user(Principal principal) {
	  Map<String, String> map = new LinkedHashMap<>();
	  map.put("name", principal.getName());
	  return map;
	}
	
	@Override
	@Bean("authenticationManagerBean")
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		return super.authenticationManagerBean();
	}

	@Bean
	public TokenStore tokenStore()
	{
		return new InMemoryTokenStore();
	}
	
	@Bean
	@Autowired
	public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore)
	{
		TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
		handler.setTokenStore(tokenStore);
		handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
		handler.setClientDetailsService(clientDetailsService);
		return handler;
	}

	@Bean
	@Autowired
	public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception
	{
		TokenApprovalStore store = new TokenApprovalStore();
		store.setTokenStore(tokenStore);
		return store;
	}
	
	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration  extends ResourceServerConfigurerAdapter {
	  @Override
	  public void configure(HttpSecurity http) throws Exception {
	    http
	      .antMatcher("/me")
	      .authorizeRequests().anyRequest().authenticated();
	  }
	  
	}
}
