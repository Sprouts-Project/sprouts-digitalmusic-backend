package org.sprouts.digitalmusic.backend.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sprouts.digitalmusic.backend.da.UserDAO;
import org.sprouts.digitalmusic.model.User;


@Service("userService")
public class UserService {

	// Managed Data Access Objects --------------------------------------------
	
	@Autowired
	private UserDAO userDAO;
	
	// Simple CRUD Methods ----------------------------------------------------
	
	public User findOne(int id) {
		User result;
		
		result = userDAO.findOne(id);
		
		return result;
	}
	
	public Collection<User> findAll() {
		Collection<User> result;
		
		result = (Collection<User>) userDAO.findAll();
		
		return result;
	}
	
	public int save(User user) {
		userDAO.save(user);
		
		return 1;
	}
	
	public int delete(User user) {
		userDAO.delete(user);
		
		return 1;
	}
}