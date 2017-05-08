package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter
{
	
	private @Autowired MyUserDetailsService userDetailsService;
	private @Autowired TokenStore tokenStore;
	private @Autowired UserApprovalHandler userApprovalHandler;
	private @Autowired@Qualifier("authenticationManagerBean") AuthenticationManager authenticationManager;
	
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// @formatter:off
//		clients
//			.inMemory()
//				.withClient("test")
//					.authorizedGrantTypes((String[]) clientDetails.getAuthorizedGrantTypes().toArray())
//					.authorities((String[]) clientDetails.getAuthorities().toArray())
//					.scopes("read","write")
//					.resourceIds((String[]) clientDetails.getResourceIds().toArray())
//					.secret(clientDetails.getClientSecret())
//					.accessTokenValiditySeconds(clientDetails.getAccessTokenValiditySeconds())
//					.refreshTokenValiditySeconds(clientDetails.getRefreshTokenValiditySeconds());
		// @formatter:on
	        clients.inMemory()
	            .withClient("test")
	            .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
	            .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
	            .scopes("read", "write", "trust")
	            .secret("test123")
	            .accessTokenValiditySeconds(300).//Access token is only valid for 2 minutes.
	            refreshTokenValiditySeconds(600);//Refresh token is only valid for 10 minutes.
	}
	
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    	
//    	ClientDetailsService clientDetailsService = endpoints.getClientDetailsService();
//		userApprovalHandler.setClientDetailsService(clientDetailsService);
//    	userApprovalHandler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
        endpoints.tokenStore(tokenStore).userApprovalHandler(userApprovalHandler)
                .authenticationManager(authenticationManager).userDetailsService(userDetailsService);
    }
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception
    {
    	security.checkTokenAccess("permitAll()");
    	super.configure(security);
    }
}
