package com.addalia.simplemonitor.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.addalia.simplemonitor.persistence.entity.AppUserRole;
import com.addalia.simplemonitor.persistence.repository.IAppUserRoleRepository;

@Service
public class AppUserRoleServiceImpl implements IAppUserRoleService{

	@Autowired
	IAppUserRoleRepository roleRepository;
	
	@Override
	public List<AppUserRole> findAll() {
		return (List<AppUserRole>)roleRepository.findAll();
	}

}
