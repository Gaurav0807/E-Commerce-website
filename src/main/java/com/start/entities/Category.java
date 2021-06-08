package com.start.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class Category {
	
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Size(min=2, message = "Name must be at least 2 characters long")
    private String name;

    private String slug;

    @Size(min=5, message = "Content must be at least 5 characters long")
    private String content;

    private int sorting;

    
	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getSorting() {
		return sorting;
	}

	public void setSorting(int sorting) {
		this.sorting = sorting;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
    
    
    

}
