package org.sprouts.digitalmusic.backend.da;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.sprouts.digitalmusic.model.Item;


@Transactional
public interface ItemDAO extends PagingAndSortingRepository<Item, Integer> {

	Page<Item> findAll(Pageable pageable);
}