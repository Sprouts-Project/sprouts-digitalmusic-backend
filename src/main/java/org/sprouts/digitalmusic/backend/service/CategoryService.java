package org.sprouts.digitalmusic.backend.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sprouts.digitalmusic.backend.da.CategoryDAO;
import org.sprouts.digitalmusic.model.Category;

@Service
public class CategoryService {

	// Managed Data Access Objects --------------------------------------------

	@Autowired
	private CategoryDAO categoryDAO;

	// Simple CRUD Methods ----------------------------------------------------


	

	public Collection<Category> findAll() {
		
		return (Collection<Category>) categoryDAO.findAll();
	}
}