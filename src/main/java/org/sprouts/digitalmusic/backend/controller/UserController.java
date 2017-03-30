package org.sprouts.digitalmusic.backend.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

import org.sprouts.digitalmusic.backend.service.UserService;
import org.sprouts.digitalmusic.model.User;
import org.sprouts.digitalmusic.pojo.ResponseObject;

@RestController
@Api
@RequestMapping("/user")
public class UserController extends AbstractController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<User> userInfo() throws Exception {
		Collection<User> result = userService.findAll();
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseObject createUser(@RequestBody User user) throws Exception {
		return getResponseObject(userService.save(user));
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseObject updateUser(@RequestBody User user) throws Exception {
		return getResponseObject(userService.save(user));
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseObject deleteUser(@RequestBody User user) throws Exception {
		return getResponseObject(userService.delete(user));
	}
}