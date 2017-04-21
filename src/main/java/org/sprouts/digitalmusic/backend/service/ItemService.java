package org.sprouts.digitalmusic.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.sprouts.digitalmusic.backend.da.ItemDAO;
import org.sprouts.digitalmusic.model.Item;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;


@Service
public class ItemService {

	@Autowired
	private ItemDAO itemDAO;

	@Autowired
	private KieContainer kieContainer;

	public int save(Item item){
		itemDAO.save(item);
		return 1;
	}

	public int delete(Item item){
		int res;
		KieSession kieSession = kieContainer.newKieSession("Session");
		kieSession.insert(item);
		kieSession.setGlobal("itemService", this);

		try{
			kieSession.fireAllRules();
			itemDAO.delete(item);
			res = 1;
		}catch(Exception e){
			res = 0;
		}

		return res;
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