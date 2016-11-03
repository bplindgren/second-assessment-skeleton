package com.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.User;

@Transactional
public interface UserRepository extends JpaRepository<User, Long>{
	
	
	
}
