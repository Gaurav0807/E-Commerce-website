package com.start.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.start.entities.Category;

import com.start.entities.Product;
import com.start.repo.CategoryRepository;
import com.start.repo.ProductRepository;

@Controller
@RequestMapping("/admin/product")
public class AdminProductController {

	@Autowired
	private ProductRepository p;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping
	public String index(Model m,@RequestParam(value="page", required=false) Integer a)
	{
		int perPage=2;
		int page = (a!=null) ? a : 0;
		Pageable pageable = PageRequest.of(page, perPage);
		Page<Product> list = p.findAll(pageable);
		List<Category> c = categoryRepository.findAll();
		HashMap<Integer,String> c1 = new HashMap<>();
		for(Category c2:c)
		{
			c1.put(c2.getId(), c2.getName());
		}
		m.addAttribute("product", list);
		m.addAttribute("c1", c1);
		
		Long count=p.count();
		double pagecount = Math.ceil((double)count/(double)perPage);
		m.addAttribute("pageCount", (int)pagecount);
		m.addAttribute("perPage", perPage);
		m.addAttribute("count", count);
		m.addAttribute("page",page);
		return "admin/product/index";
	}
	
	@GetMapping("/add")
	public String add(Product product ,Model m)
	{
		List<Category> list = categoryRepository.findAll();
		m.addAttribute("category", list);
		return "admin/product/add";
	}
	
	@PostMapping("/add")
	public String add(@Valid Product product ,BindingResult b,MultipartFile file ,RedirectAttributes red, Model m) throws IOException
	{
		if(b.hasErrors())
		{
			return "admin/product/add";
		}
		
		
		boolean fileOk=false;
		byte[] bytes = file.getBytes();
		String filename = file.getOriginalFilename();
		Path path = Paths.get("src/main/resources/static/media/"+filename);
		
		if(filename.endsWith("jpg") || filename.endsWith("png") ||  filename.endsWith("jpeg"))
		{
			fileOk=true;
		}
		
		red.addFlashAttribute("message", "Product Added");
		red.addFlashAttribute("alertclass", "alert-success");
		
		String slug = product.getName().toLowerCase().replace(" ","-");
		
		Product productExists=p.findByName(slug);
		
		if(productExists!=null)
		{
			red.addFlashAttribute("message", "Product exists ,choose another");
			red.addFlashAttribute("alertclass", "alert-danger");
		}
		else{
			product.setName(slug);
			product.setImage(filename);
			p.save(product);
			Files.write(path, bytes);
		}			
		return "redirect:/admin/product/add";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id)
	{
		p.deleteById(id);
		return "redirect:/admin/product";
	}
	
	
	
	
	
}
