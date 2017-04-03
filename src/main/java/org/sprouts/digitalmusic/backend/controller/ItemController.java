package org.sprouts.digitalmusic.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sprouts.digitalmusic.backend.service.ItemService;
import org.sprouts.digitalmusic.model.Item;
import org.sprouts.digitalmusic.pojo.PaginationInfo;

import io.swagger.annotations.Api;

@RestController
@Api
@RequestMapping("/item")
public class ItemController extends AbstractController {

	@Autowired
	private ItemService itemService;

	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object[] list(@RequestParam int pageNumber) throws Exception {
		Object[] result = new Object[2];
		
		Page<Item> page = itemService.findAll(pageNumber);
		
		result[0] = page.getContent();
		result[1] = new PaginationInfo(pageNumber, page.getTotalPages(), page.getNumberOfElements(), 9);
		
		return result;
	}
}