package org.sprouts.digitalmusic.backend.controller;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sprouts.digitalmusic.backend.security.UserDetailsService;
import org.sprouts.digitalmusic.backend.service.CustomerService;
import org.sprouts.digitalmusic.backend.service.ItemService;
import org.sprouts.digitalmusic.backend.service.ReviewService;
import org.sprouts.digitalmusic.model.Item;
import org.sprouts.digitalmusic.model.Review;
import org.sprouts.digitalmusic.pojo.ResponseObject;

import io.swagger.annotations.Api;

@RestController
@Api
@RequestMapping("/review")
public class ReviewController extends AbstractController {

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "/findByItem", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Review> findByItem(@RequestParam int itemId) throws Exception {
		
		Item item = itemService.findOne(itemId);
		return reviewService.findReviewsOfItem(item);
		
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseObject createReview(@RequestBody Review review,@RequestParam int itemId) throws Exception {
		review.setCustomer(customerService.findByUsername(UserDetailsService.getPrincipal().getUsername()));
		review.setItem(itemService.findOne(itemId));
		review.setHelpful(0);
		review.setOverall(0);
		review.setUnhelpful(0);
		return getResponseObject(reviewService.save(review));
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseObject deleteReview(@RequestBody Review review) throws Exception {
		return getResponseObject(reviewService.delete(review));
	}




}