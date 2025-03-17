package com.addalia.simplemonitor.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.addalia.simplemonitor.persistence.entity.AppUser;
import com.addalia.simplemonitor.persistence.service.IAppUserService;

@Service
public class UserDetailServiceImpl implements UserDetailsService{

	
	@Autowired
	IAppUserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		AppUser appUser = userService.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
		
		
		return new UserDetailsImpl(appUser);
		
	}

}
