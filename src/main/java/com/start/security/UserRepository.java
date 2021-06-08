package com.start.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.start.entities.User;

public interface UserRepository extends JpaRepository<User,Integer> {

	

	User findByUsername(String username);
}
