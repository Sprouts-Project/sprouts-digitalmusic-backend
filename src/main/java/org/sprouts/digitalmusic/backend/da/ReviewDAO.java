package org.sprouts.digitalmusic.backend.da;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.sprouts.digitalmusic.model.Customer;
import org.sprouts.digitalmusic.model.Item;
import org.sprouts.digitalmusic.model.Review;


@Transactional
public interface ReviewDAO extends CrudRepository<Review, Integer> {

	@Query("select r from Review r where r.item=?1")
    Collection<Review> findReviewsOfItem(Item item);
	
	@Query("select r from Review r where r.customer=?1")
    Collection<Review> findReviewsOfCustomer(Customer customer);
}