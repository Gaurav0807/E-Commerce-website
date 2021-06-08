package com.start.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.start.entities.Page;
import com.start.repo.Pagerepository;

@Controller
@RequestMapping("/")
public class PageController {
	
	@Autowired
	private Pagerepository pageRepository;
	
	@GetMapping
	public String home(Model m)
	{
		Page page = pageRepository.findBySlug("home");
		m.addAttribute("page", page);
		return "page";
	}
	
	@GetMapping("/login")
	public String login()
	{
		return "login";
	}
	
	@GetMapping("/{slug}")
	public String page(@PathVariable String slug,Model m)
	{
		Page page = pageRepository.findBySlug(slug);
		if(page == null)
		{
			return "redirect:/";
		}
		
		m.addAttribute("page", page);
		return "page";
	}

}
