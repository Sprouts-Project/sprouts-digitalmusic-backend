package org.sprouts.digitalmusic.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.sprouts.digitalmusic.backend.service.AuthorityService;
import org.sprouts.digitalmusic.model.Authority;

import io.swagger.annotations.Api;

@RestController
@Api
@RequestMapping("/authority")
public class AuthorityController extends AbstractController {

	@Autowired
	private AuthorityService authorityService;

	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Authority userInfo() throws Exception {
		Authority result = authorityService.findByPrincipal();
		return result;
	}
}