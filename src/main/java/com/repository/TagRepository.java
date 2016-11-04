package com.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Tag;


@Transactional
public interface TagRepository extends JpaRepository<Tag, Long> {
	
	List<Tag> findAll();
	
	Tag findByLabel(String label);
	
}

