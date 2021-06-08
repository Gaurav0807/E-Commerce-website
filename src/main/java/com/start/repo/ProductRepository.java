package com.start.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.start.entities.Product;

public interface ProductRepository extends JpaRepository<Product,Integer>{

	Product findByName(String name);

	Product findByNameAndIdNot(String name, int id);
	
	Page<Product> findAll(Pageable pageable);


	
	List<Product> findAllByCategoryId(String categoryId, Pageable pageable);

    long countByCategoryId(String categoryId);

	Product findByDescription(String description);


}
