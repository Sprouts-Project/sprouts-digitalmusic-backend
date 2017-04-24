package org.sprouts.digitalmusic.backend.service;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sprouts.digitalmusic.backend.da.ReviewDAO;
import org.sprouts.digitalmusic.backend.security.UserDetailsService;
import org.sprouts.digitalmusic.model.Item;
import org.sprouts.digitalmusic.model.Review;

@Service
public class ReviewService {

	// Managed Data Access Objects --------------------------------------------

	@Autowired
	private ReviewDAO reviewDAO;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ItemService itemService;

	// Simple CRUD Methods ----------------------------------------------------

	public int save(Review review, int itemId){
		review.setCustomer(customerService.findByUsername(UserDetailsService.getPrincipal().getUsername()));
		review.setItem(itemService.findOne(itemId));
		review.setHelpful(0);
		review.setUnhelpful(0);
		review.setDate(new Date());
		reviewDAO.save(review);

		return 1;
	}

	public Collection<Review> findReviewsOfItem(Item item) {
		return reviewDAO.findReviewsOfItem(item);
	}
}