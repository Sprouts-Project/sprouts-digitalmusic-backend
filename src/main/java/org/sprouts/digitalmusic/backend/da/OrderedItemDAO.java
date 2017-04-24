package org.sprouts.digitalmusic.backend.da;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.sprouts.digitalmusic.model.OrderedItem;

@Transactional
public interface OrderedItemDAO extends CrudRepository<OrderedItem, Integer>{
}
