package com.addalia.simplemonitor.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.addalia.simplemonitor.persistence.entity.AppUser;


public class UserDetailsImpl implements UserDetails {
	
	private final AppUser appUser;

	public UserDetailsImpl(AppUser appUser) {
		this.appUser = appUser;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {		
		return appUser.getRoles().stream()
	    .map(role -> new SimpleGrantedAuthority(role.getName()))
	    .collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return appUser.getPassword();
	}

	@Override
	public String getUsername() {
		return appUser.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		return appUser.getEnabled();
	}
	
	public String getEmail() {
		return appUser.getEmail();
	}
	
	public String getFullname() {
		return appUser.getName() + " " + appUser.getLastName();
	}
	
	public Long getUserId() {
		return appUser.getId();
	}

	private static final long serialVersionUID = -3566560077966071282L;
}
