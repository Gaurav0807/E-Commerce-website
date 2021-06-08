package com.start.security;
import com.start.entities.Admin;
import com.start.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Integer> {

	
	Admin findByUsername(String username);
}
