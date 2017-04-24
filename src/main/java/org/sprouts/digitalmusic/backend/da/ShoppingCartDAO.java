package org.sprouts.digitalmusic.backend.da;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.sprouts.digitalmusic.model.ShoppingCart;

@Transactional
public interface ShoppingCartDAO extends CrudRepository<ShoppingCart, Integer>{
    ShoppingCart findShoppingCartByCustomerId(int id);
}
