package org.sprouts.digitalmusic.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.sprouts.digitalmusic.backend.da.ItemDAO;
import org.sprouts.digitalmusic.model.Item;


@Service
public class ItemService {

	// Managed Data Access Objects --------------------------------------------
	
	@Autowired
	private ItemDAO itemDAO;
	
	// Simple CRUD Methods ----------------------------------------------------
	
	public Item findOne(int id) {
		return itemDAO.findOne(id);
	}
	
	public Page<Item> findAll(int pageNumber) {
		Assert.isTrue(pageNumber >= 0);
		
		Pageable pageable = new PageRequest(pageNumber, 9);
		
		return itemDAO.findAll(pageable);
	}
}