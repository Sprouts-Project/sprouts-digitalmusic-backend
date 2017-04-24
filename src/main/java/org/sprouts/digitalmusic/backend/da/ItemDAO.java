package org.sprouts.digitalmusic.backend.da;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.sprouts.digitalmusic.model.Customer;
import org.sprouts.digitalmusic.model.Item;


@Transactional
public interface ItemDAO extends PagingAndSortingRepository<Item, Integer> {

	Page<Item> findAll(Pageable pageable);
	
	@Query("select o.item from OrderedItem o where o.order.customer=?1")
	Collection<Item> itemBoughtByCostumer(Customer customer);
}