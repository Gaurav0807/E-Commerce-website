package com.start.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.start.entities.Category;


public interface CategoryRepository extends JpaRepository<Category,Integer> {
	
	Category findByName(String name);

	List<Category> findAllByOrderBySortingAsc();

   

}
