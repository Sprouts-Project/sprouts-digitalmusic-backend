package org.sprouts.digitalmusic.backend.da;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.sprouts.digitalmusic.model.Order;

@Transactional
public interface OrderDAO extends CrudRepository<Order,Integer> {

}
