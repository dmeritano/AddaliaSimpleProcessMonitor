package com.addalia.simplemonitor.persistence.service;

import java.util.List;
import java.util.Optional;

import com.addalia.simplemonitor.persistence.entity.AppUser;

public interface IAppUserService {

	public List<AppUser> findAll();
	public void save(AppUser appUser);
	public Optional<AppUser> findByUsername(String username);
	public Optional<AppUser> findByEmail(String email);
	
	
}
