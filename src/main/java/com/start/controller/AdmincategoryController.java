package com.start.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.start.entities.Category;
import com.start.entities.Page;
import com.start.repo.CategoryRepository;

@Controller
@RequestMapping("/admin/category")
public class AdmincategoryController {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping
	public String index(Model m)
	{
		List<Category> category = categoryRepository.findAll();
		m.addAttribute("category", category);
		return "admin/category/index";
	}
	
	@GetMapping("/add")
	public String add(Category category)
	{
		return "admin/category/add";
	}
	
	@PostMapping("/add")
	public String add(@Valid Category category ,BindingResult b ,RedirectAttributes red, Model m)
	{
		if(b.hasErrors())
		{
			return "admin/pages/add";
		}
		red.addFlashAttribute("message", "Page Added");
		red.addFlashAttribute("alertclass", "alert-success");
		
		String name = category.getName().toLowerCase().replace(" ","-");
		
		Category nameExists=categoryRepository.findByName(category.getName());
		
		if(nameExists!=null)
		{
			red.addFlashAttribute("message", "Slug exists ,choose another");
			red.addFlashAttribute("alertclass", "alert-danger");
		}
		else{
			
			category.setName(name);
			category.setSorting(100);
			categoryRepository.save(category);
		}
				
		return "redirect:/admin/category/add";
	}
	
//	@GetMapping("/edit/{id}")
//	public String edit(@PathVariable int id, Model m)
//	{
//		Category c = categoryRepository.getOne(id);
//		m.addAttribute("category", c);
//		return "admin/category/edit";
//	}
//	
//	
//	@PostMapping("/edit")
//	public String edit(@Valid Category category,BindingResult b ,RedirectAttributes red, Model m)
//	{
//		if(b.hasErrors())
//		{
//			return "admin/category/edit";
//		}
//		red.addFlashAttribute("message", "Page Edited");
//		red.addFlashAttribute("alertclass", "alert-success");
//		
//		String name = category.getName().toLowerCase().replace(" ","-");
//		
//		Category nameExists=categoryRepository.findByName(name);
//		
//		if(nameExists!=null)
//		{
//			red.addFlashAttribute("message", "Cateory exists ,choose another");
//			red.addFlashAttribute("alertclass", "alert-danger");
//			
//		}
//		else{
//			category.setName(name);
//			
//			categoryRepository.save(category);
//		}
//				
//		return "redirect:/admin/category/edit/" + category.getId();
//	}
	
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id)
	{
		categoryRepository.deleteById(id);
		return "redirect:/admin/category";
	}
	
}
