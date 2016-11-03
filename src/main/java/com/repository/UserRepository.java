package com.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.User;

@Transactional
public interface UserRepository extends JpaRepository<User, Long>{
	
	public List<User> findAll();
	
}
