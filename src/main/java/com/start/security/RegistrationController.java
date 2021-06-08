package com.start.security;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.start.entities.User;
import org.springframework.validation.Errors;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping
	public String register(Model m)
	{
		User user = new User();
		m.addAttribute("user", user);
		return "register";
	}
	
	@PostMapping
	public String register(@Valid User user,BindingResult bindingResult , Model m)
	{
		if(bindingResult.hasErrors())
		{
			System.out.println("Binding result error");
			return "register";
		}
		if(user==null)
		{
		m.addAttribute("data","Enter the data");
		return "register";
		}
		if(!user.getPassword().equals(user.getConfirmPassword()))
		{
			m.addAttribute("passwordMatcherProblem","Password do not match");
			return "register";
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		 userRepository.save(user);
		return "redirect:/register";	
	}
}
