package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@Qualifier
public class OAuth2SecurityConfiguration extends WebSecurityConfigurerAdapter {

//	@Autowired
//	private ClientDetailsService clientDetailsService;
	
	private @Autowired ResourceServerTokenServices tokenService;
	

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    OAuth2AuthenticationManager authenticationManager = new OAuth2AuthenticationManager();
	    authenticationManager.setTokenServices(tokenService);
	    return authenticationManager;
	}
	
//	@Bean
//	public ResourceServerTokenServices tokenService() {
//	    RemoteTokenServices tokenServices = new RemoteTokenServices();
//	    tokenServices.setClientId("test");
//	    tokenServices.setClientSecret("test123");
//	    tokenServices.setCheckTokenEndpointUrl("http://localhost:8080/oauth/check_token");
//	    return tokenServices;
//	}

	@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}

//	@Bean
//	@Autowired
//	public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore){
//		TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
//		handler.setTokenStore(tokenStore);
//		handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
//		handler.setClientDetailsService(clientDetailsService);
//		return handler;
//	}
	
	@Bean
	@Autowired
	public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
		TokenApprovalStore store = new TokenApprovalStore();
		store.setTokenStore(tokenStore);
		return store;
	}
	
}
