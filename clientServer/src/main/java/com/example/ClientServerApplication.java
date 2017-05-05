package com.example;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableAutoConfiguration
@Configuration
@EnableOAuth2Sso
@RestController
public class ClientServerApplication extends WebSecurityConfigurerAdapter {

	private @Qualifier("userInfoTokenServices") ResourceServerTokenServices userInfoTokenServices;
	
	private @Value("${security.oauth2.client.client-id}")String clientId;
	
	private @Value("${security.oauth2.client.client-secret}")String secret;
	
	private @Value("${security.oauth2.client.token-name}")String tokenName;
	
	private @Value("${security.oauth2.client.check-token-uri}")String checkTokenUri;
	
	public static void main(String[] args) {
		SpringApplication.run(ClientServerApplication.class, args);
	}
	  @RequestMapping("/")
	  public String home(Principal user) {
	    return "Hello " + user.getName();
	  }
//	  @Bean
//	  public ResourceServerTokenServices tokenService() {
//	      RemoteTokenServices tokenServices = new RemoteTokenServices();
//	      tokenServices.setClientId("user");
//	      tokenServices.setClientSecret("password");
//	      tokenServices.setTokenName("tokenName");
//	      tokenServices.setCheckTokenEndpointUrl("http://localhost:8080/oauth/check_token");
//	      return tokenServices;
//	  }
//
//	  @Override
//	  public AuthenticationManager authenticationManagerBean() throws Exception {
//	      OAuth2AuthenticationManager authenticationManager = new OAuth2AuthenticationManager();
//	      authenticationManager.setTokenServices(tokenService());
//	      return authenticationManager;
//	  }
	  
	  @Bean
	  public RestTemplate oAuthRestTemplate() {
	      ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
	      resourceDetails.setId("1");
	      resourceDetails.setClientId(clientId);
	      resourceDetails.setClientSecret(secret);
	      resourceDetails.setAccessTokenUri(checkTokenUri);


//	          OAuth2RestTemplate restTemplate = new      OAuth2RestTemplate(resourceDetails, oauth2ClientContext);
	      OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails, new DefaultOAuth2ClientContext());

	      return restTemplate;
	  }


}
