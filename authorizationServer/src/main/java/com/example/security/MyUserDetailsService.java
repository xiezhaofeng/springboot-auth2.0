package com.example.security;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailsService implements UserDetailsService
{

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		 UserDetails userDetails = null;
	        try {
	            UserInfo favUser = new UserInfo();
	            favUser.setUsername("admin");
	            favUser.setPassword("admin");
	            Collection<GrantedAuthority> authList = getAuthorities();
	            userDetails = new User(username, favUser.getPassword().toLowerCase(),true,true,true,true,authList);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return userDetails;
	}

	private Collection<GrantedAuthority> getAuthorities()
	{
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
//        authList.add(new SimpleGrantedAuthority("ROLE_USER"));
//        authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        authList.add(new SimpleGrantedAuthority("USER"));
        authList.add(new SimpleGrantedAuthority("ADMIN"));

        return authList;
	}
	
}
