package org.sprouts.digitalmusic.backend.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sprouts.digitalmusic.backend.da.ItemDAO;
import org.sprouts.digitalmusic.model.Item;


@Service
public class ItemService {

	// Managed Data Access Objects --------------------------------------------
	
	@Autowired
	private ItemDAO itemDAO;
	
	// Simple CRUD Methods ----------------------------------------------------
	
	public Item findOne(int id) {
		Item result;
		
		result = itemDAO.findOne(id);
		
		return result;
	}
	
	public Collection<Item> findAll() {
		Collection<Item> result;
		
		result = (Collection<Item>) itemDAO.findAll();
		
		return result;
	}
}