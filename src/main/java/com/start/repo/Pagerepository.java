package com.start.repo;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.start.entities.*;
public interface Pagerepository extends JpaRepository<Page,Integer> {
	
	@Override
	List<Page> findAll();

	Page findBySlug(String slug);

	Page getOne(int id);
	
	@Query("SELECT p FROM Page p WHERE p.id !=:id and p.slug = :slug")
	Page findBySlug(int id,String slug);

	List<Page> findAllByOrderBySortingAsc();
}
