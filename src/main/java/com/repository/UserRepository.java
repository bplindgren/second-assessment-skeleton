package com.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.User;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
	
	List<User> findAll();
	
	User findByUsername(String username);
	
	User findByUsernameAndActiveTrue(String username);

	User findByCredentialsUsernameAndCredentialsPassword(String username, String password);
	
	User findByCredentialsUsernameAndCredentialsPasswordAndActiveFalse(String username, String password);
}
