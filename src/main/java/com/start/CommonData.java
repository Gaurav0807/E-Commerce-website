package com.start;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.start.entities.Cart;
import com.start.entities.Category;
import com.start.entities.Page;
import com.start.repo.CategoryRepository;
import com.start.repo.Pagerepository;

@ControllerAdvice
@SuppressWarnings("unchecked")
public class CommonData {

	@Autowired
	private Pagerepository pageRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@ModelAttribute
	public void sharedData(Model m,HttpSession session,Principal principal )
	{
		if(principal!=null)
		{
			m.addAttribute("principal", principal.getName());
		}
		List<Page> page = pageRepository.findAllByOrderBySortingAsc();
		
		List<Category> category = categoryRepository.findAll();
		boolean cartActive = false;
		
		if(session.getAttribute("cart")!=null)
		{
			HashMap<Integer,Cart> cart= (HashMap<Integer, Cart>) session.getAttribute("cart");
			
			int size = 0;
			double total = 0;
			
			for(Cart c : cart.values())
			{
				size+=c.getQuantity();
				total+=c.getQuantity()*Double.parseDouble(c.getPrice());
			}
			m.addAttribute("csize", size);
			m.addAttribute("ctotal", total);
			
			cartActive = true;
	
		}
		 m.addAttribute("cpages", page);
		 m.addAttribute("ccategory", category);
		 m.addAttribute("cartActive", cartActive);
	}
	
	
	
}
