package org.sprouts.digitalmusic.backend.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sprouts.digitalmusic.backend.service.ItemService;
import org.sprouts.digitalmusic.model.Item;
import org.sprouts.digitalmusic.pojo.PaginationInfo;
import org.sprouts.digitalmusic.pojo.ResponseObject;

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

	@RequestMapping(value = "/display", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object[] view(@RequestParam int itemId) {
		Object[] result = new Object[2];

		result[0] = itemService.findOne(itemId);
		result[1] = itemService.getNumberOfSales(itemId);

		return result;
	}

	@RequestMapping(value = "/admin/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseObject updateItem(@RequestBody Item item) throws Exception {
		return getResponseObject(itemService.save(item));
	}

	@RequestMapping(value = "/admin/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseObject deleteItem(@RequestBody Item item) throws Exception {
		return getResponseObject(itemService.delete(item));
	}
	
	@RequestMapping(value = "/itemBoughtByCostumer", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean itemBoughtByCostumer(@RequestParam int itemId) throws Exception {
		return itemService.itemBoughtByCostumer(itemId);
	}
}