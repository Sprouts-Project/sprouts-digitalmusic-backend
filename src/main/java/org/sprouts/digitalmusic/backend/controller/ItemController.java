package org.sprouts.digitalmusic.backend.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.sprouts.digitalmusic.backend.service.ItemService;
import org.sprouts.digitalmusic.model.Item;

import io.swagger.annotations.Api;

@RestController
@Api
@RequestMapping("/item")
public class ItemController extends AbstractController {

	@Autowired
	private ItemService itemService;

	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Item> userInfo() throws Exception {
		Collection<Item> result = itemService.findAll();
		return result;
	}
}