package org.sprouts.digitalmusic.backend.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.sprouts.digitalmusic.backend.service.CategoryService;
import org.sprouts.digitalmusic.model.Category;

@RestController
@RequestMapping("/category")
public class CategoryController extends AbstractController {

	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Category> list() throws Exception {
		return categoryService.findAll();
	}






}