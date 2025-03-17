package com.addalia.simplemonitor.persistence.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.addalia.simplemonitor.persistence.entity.AppUser;
import com.addalia.simplemonitor.persistence.repository.IAppUserRepository;

@Service
public class AppUserServiceImpl implements IAppUserService{

	@Autowired
	IAppUserRepository userRepository;
	
	@Override
	@Transactional(readOnly=true)	
	public List<AppUser> findAll() {		
		return (List<AppUser>)userRepository.findAll();		
	}

	@Override
	@Transactional
	public void save(AppUser appUser) {
		userRepository.save(appUser);
		
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<AppUser> findByUsername(String username) {
		return userRepository.findOneByUsername(username);
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<AppUser> findByEmail(String email) {
		return userRepository.findOneByEmail(email);
	}

}
