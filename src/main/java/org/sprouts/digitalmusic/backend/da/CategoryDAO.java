package org.sprouts.digitalmusic.backend.da;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.sprouts.digitalmusic.model.Category;


@Transactional
public interface CategoryDAO extends CrudRepository<Category, Integer> {

	
	
	
	
}