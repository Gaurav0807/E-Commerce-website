package com.start.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.start.security.*;

import com.start.entities.Admin;
import com.start.entities.User;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO 
		User user = userRepository.findByUsername(username);
		Admin admin = adminRepository.findByUsername(username);
		
		if(user!=null)
		{
			 CustomUserDetail c = new CustomUserDetail(user);
			 return c;
		}
		if(admin!=null)
		{
			 CustomAdminDetail c = new CustomAdminDetail(admin);
			 return c;
		}
		
		
		throw new UsernameNotFoundException("User:"+username+"not found!");
	}

}
