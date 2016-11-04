package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

	List<Tweet> findAll();
	
	Tweet findOne(long id);
}
