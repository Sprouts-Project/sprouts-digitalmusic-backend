package org.sprouts.digitalmusic.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sprouts.digitalmusic.backend.da.ShoppingCartDAO;
import org.sprouts.digitalmusic.backend.security.UserDetailsService;
import org.sprouts.digitalmusic.model.Customer;
import org.sprouts.digitalmusic.model.ShoppingCart;

@Service
public class ShoppingCartService {

    // Managed Data Access Objects --------------------------------------------

    @Autowired
    private ShoppingCartDAO shoppingCartDAO;

    // Managed Services -------------------------------------------------------

    @Autowired
    private CustomerService customerService;

    // Simple CRUD Methods ----------------------------------------------------

    public ShoppingCart findByPrincipal(){
        Customer principal = customerService.findByUsername(UserDetailsService.getPrincipal().getUsername());
        ShoppingCart shoppingCart = shoppingCartDAO.findShoppingCartByCustomerId(principal.getId());
        return shoppingCart;
    }

    public void clear() {
        ShoppingCart shoppingCart = findByPrincipal();
        shoppingCart.getItems().clear();
        shoppingCartDAO.save(shoppingCart);
    }

    public ShoppingCart save(ShoppingCart shoppingCart) {
        return shoppingCartDAO.save(shoppingCart);
    }
}
