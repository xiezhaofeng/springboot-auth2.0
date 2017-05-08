package com.example.security;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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


@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter{

	private @Autowired MyUserDetailsService userDetailsService;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  http.antMatcher("/oauth/check_token").authorizeRequests().anyRequest().permitAll()
	  .and().antMatcher("/**")
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
	public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore, ClientDetailsService clientDetailsService) throws Exception
	{
		TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
		handler.setTokenStore(tokenStore);
		handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
		handler.setClientDetailsService(clientDetailsService);
		return handler;
	}
//	@Bean
//	public ClientDetailsService clientDetailsService() throws Exception{
//		return new InMemoryClientDetailsServiceBuilder().build();
//	}

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
