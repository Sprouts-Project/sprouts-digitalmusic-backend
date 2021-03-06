package org.sprouts.digitalmusic.backend.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.sprouts.digitalmusic.backend.da.ItemDAO;
import org.sprouts.digitalmusic.backend.security.UserDetailsService;
import org.sprouts.digitalmusic.backend.exceptions.EntityNotFoundException;
import org.sprouts.digitalmusic.backend.exceptions.UserNotLoggedInException;
import org.sprouts.digitalmusic.model.Customer;
import org.sprouts.digitalmusic.model.Item;

@Service
public class ItemService {

	@Autowired
    private CustomerService customerService;
	
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

		Pageable pageable = new PageRequest(pageNumber, 12	);

		return itemDAO.findAll(pageable);
	}
	
	public boolean itemBoughtByCostumer(int itemId) throws Exception {
		Customer customer;

	    try {
            customer = customerService.findByUsername(UserDetailsService.getPrincipal().getUsername());
        } catch(Exception oops) {
            throw new UserNotLoggedInException("Unable to retrieve current principal. Are you logged in?");
        }

		Item item = findOne(itemId);

		if (item == null) {
			throw new EntityNotFoundException("Unable to find an Item with the provided identifier");
		}

		Collection<Item> items = itemDAO.itemBoughtByCostumer(customer);

		return items.contains(item);
	}

	public int getNumberOfSales(int itemId){
		Item item = findOne(itemId);

		return itemDAO.numberOfSales(item);
	}
}