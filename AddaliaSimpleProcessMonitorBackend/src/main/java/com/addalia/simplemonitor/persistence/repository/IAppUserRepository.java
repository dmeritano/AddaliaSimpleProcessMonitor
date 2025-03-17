package com.addalia.simplemonitor.persistence.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.addalia.simplemonitor.persistence.entity.AppUser;


public interface IAppUserRepository extends CrudRepository<AppUser, Long>{

	public List<AppUser> findAll();

	public Optional<AppUser> findOneByUsername(String username);
	public Optional<AppUser> findOneByEmail(String email);	
	
}
