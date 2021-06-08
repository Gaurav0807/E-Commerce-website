package com.start.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.start.entities.Category;
import com.start.entities.Product;
import com.start.repo.CategoryRepository;
import com.start.repo.ProductRepository;

@Controller
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	 @GetMapping("/{name}")
	    public String category(@PathVariable String name, Model model, @RequestParam(value="page", required = false) Integer p) {

	        int perPage = 3;
	        int page = (p != null) ? p : 0;
	        Pageable pageable = PageRequest.of(page, perPage);
	        long count = 0;

	        if (name.equals("all")) {

	            Page<Product> products = productRepository.findAll(pageable);

	            count =productRepository.count();

	            model.addAttribute("products", products);
	        } else {
	        	
	            Category category = categoryRepository.findByName(name);

	            if (category == null) {
	                return "redirect:/";
	            }

	            int categoryId = category.getId();
	            String categoryName = category.getName();
	            List<Product> products = productRepository.findAllByCategoryId(Integer.toString(categoryId), pageable);

	            count = productRepository.countByCategoryId(Integer.toString(categoryId));

	            model.addAttribute("products", products);
	            model.addAttribute("categoryName", categoryName);
	        }

	        double pageCount = Math.ceil((double)count / (double)perPage);

	        model.addAttribute("pageCount", (int)pageCount);
	        model.addAttribute("perPage", perPage);
	        model.addAttribute("count", count);
	        model.addAttribute("page", page);

	        return "product";
	    }

	
	
}
