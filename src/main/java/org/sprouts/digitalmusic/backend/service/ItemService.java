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

	@Autowired
	private ItemDAO itemDAO;

	public int save(Item item){
		itemDAO.save(item);
		return 1;
	}

	public int delete(Item item){
		itemDAO.delete(item);
		return 1;
	}

	public Item findOne(int id) {
		return itemDAO.findOne(id);
	}

	public Page<Item> findAll(int pageNumber) {
		Assert.isTrue(pageNumber >= 0);

		Pageable pageable = new PageRequest(pageNumber, 9);

		return itemDAO.findAll(pageable);
	}
}