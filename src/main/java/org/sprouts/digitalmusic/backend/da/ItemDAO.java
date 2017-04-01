package org.sprouts.digitalmusic.backend.da;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.sprouts.digitalmusic.model.Item;


@Transactional
public interface ItemDAO extends CrudRepository<Item, Integer> {

}