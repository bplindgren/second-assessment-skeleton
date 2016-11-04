package com.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Credentials;

@Transactional
public interface CredentialsRepository extends JpaRepository<Credentials, Long> {

	
}
