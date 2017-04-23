package org.sprouts.digitalmusic.backend.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sprouts.digitalmusic.backend.da.ReviewDAO;
import org.sprouts.digitalmusic.model.Item;
import org.sprouts.digitalmusic.model.Review;

@Service
public class ReviewService {

	// Managed Data Access Objects --------------------------------------------

	@Autowired
	private ReviewDAO reviewDAO;

	// Simple CRUD Methods ----------------------------------------------------

	public int save(Review review){

		reviewDAO.save(review);
		return 1;
	}

	public int delete(Review review){
		reviewDAO.delete(review);
		return 1;
	}
	

	public Collection<Review> findReviewsOfItem(Item item) {
		
		return reviewDAO.findReviewsOfItem(item);
	}
}