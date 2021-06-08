package com.start.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.start.entities.Page;
import com.start.repo.Pagerepository;

@Controller
@RequestMapping("/admin/pages")
public class AdminPagesController {
	
	@Autowired
	private Pagerepository pageRepository;


	@GetMapping
	public String index(Model m)
	{
		List<Page> pages = pageRepository.findAll();
		m.addAttribute("pages", pages);
		return "admin/pages/index.html";
	}
	
	@GetMapping("/add")
	public  String add(@ModelAttribute Page p)
	{
		
		return "admin/pages/add";
	}
	
	@PostMapping("/add")
	public String add(@Valid Page page,BindingResult b ,RedirectAttributes red, Model m)
	{
		if(b.hasErrors())
		{
			return "admin/pages/add";
		}
		red.addFlashAttribute("message", "Page Added");
		red.addFlashAttribute("alertclass", "alert-success");
		
		String slug = page.getSlug() == "" ? page.getTitle().toLowerCase().replace(" ","-"):page.getSlug().toLowerCase().replace(" ","-");
		
		Page slugExists=pageRepository.findBySlug(slug);
		
		if(slugExists!=null)
		{
			red.addFlashAttribute("message", "Slug exists ,choose another");
			red.addFlashAttribute("alertclass", "alert-danger");
			red.addFlashAttribute("page", page);
		}
		else{
			page.setSlug(slug);
			page.setSorting(100);
			pageRepository.save(page);
		}
				
		return "redirect:/admin/pages/add";
	}
	
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model m)
	{
		Page p = pageRepository.getOne(id);
		m.addAttribute("page", p);
		return "admin/pages/edit";
	}
	
	
	@PostMapping("/edit")
	public String edit(@Valid Page page,BindingResult b ,RedirectAttributes red, Model m)
	{
		if(b.hasErrors())
		{
			return "admin/pages/edit";
		}
		red.addFlashAttribute("message", "Page Edited");
		red.addFlashAttribute("alertclass", "alert-success");
		
		String slug = page.getSlug() == "" ? page.getTitle().toLowerCase().replace(" ","-"):page.getSlug().toLowerCase().replace(" ","-");
		
		Page slugExists=pageRepository.findBySlug(page.getId(),slug);
		
		if(slugExists!=null)
		{
			red.addFlashAttribute("message", "Slug exists ,choose another");
			red.addFlashAttribute("alertclass", "alert-danger");
			red.addFlashAttribute("page", page);
		}
		else{
			page.setSlug(slug);
			
			pageRepository.save(page);
		}
				
		return "redirect:/admin/pages/edit/" + page.getId();
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id)
	{
		pageRepository.deleteById(id);
		return "redirect:/admin/pages";
	}
}
