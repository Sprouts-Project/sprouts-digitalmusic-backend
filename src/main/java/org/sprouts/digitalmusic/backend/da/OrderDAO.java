package org.sprouts.digitalmusic.backend.da;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.sprouts.digitalmusic.model.Item;
import org.sprouts.digitalmusic.model.Order;

@Transactional
public interface OrderDAO extends CrudRepository<Order, Integer> {

    @Query("select o from Order o order by o.date desc")
    Page<Order> findAllOrderByDeliveredDateDesc(Pageable pageable);
}
