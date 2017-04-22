package org.sprouts.digitalmusic.backend.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sprouts.digitalmusic.backend.service.ShoppingCartService;
import org.sprouts.digitalmusic.model.Item;

import io.swagger.annotations.Api;

@RestController
@Api
@RequestMapping("/shoppingCart")
public class ShoppingCartController extends AbstractController {

	@Autowired
	private ShoppingCartService shoppingCartService;

	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Item> list() throws Exception {
		
		return shoppingCartService.findByPrincipal().getItems();
		
	}
	
	@RequestMapping(value = "/addItem", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addItem(@RequestParam int itemId) throws Exception {
		
		shoppingCartService.addItem(itemId);
		
	}

	@RequestMapping(value = "/deleteItem", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteItem(@RequestParam int itemId) throws Exception {
		
		shoppingCartService.deleteItem(itemId);
		
	}
	
	@RequestMapping(value = "/clear", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void clear() throws Exception {
		
		shoppingCartService.clear();
		
	}
	
}