package org.sprouts.digitalmusic.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sprouts.digitalmusic.backend.da.ShoppingCartDAO;
import org.sprouts.digitalmusic.backend.security.UserDetailsService;
import org.sprouts.digitalmusic.model.Customer;
import org.sprouts.digitalmusic.model.Item;
import org.sprouts.digitalmusic.model.ShoppingCart;

@Service
public class ShoppingCartService {

    // Managed Data Access Objects --------------------------------------------

    @Autowired
    private ShoppingCartDAO shoppingCartDAO;

    // Managed Services -------------------------------------------------------

    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private ItemService itemService;

    // Simple CRUD Methods ----------------------------------------------------

    public ShoppingCart findByPrincipal(){
        Customer principal = customerService.findByUsername(UserDetailsService.getPrincipal().getUsername());
        ShoppingCart shoppingCart = shoppingCartDAO.findShoppingCartByCustomerId(principal.getId());
        return shoppingCart;
    }

    public void addItem(int id){
    	Item item = itemService.findOne(id);
    	ShoppingCart shoppingCart = findByPrincipal();
    	shoppingCart.getItems().add(item);
        shoppingCartDAO.save(shoppingCart);

    }
    
    public void deleteItem(int id){
    	Item item = itemService.findOne(id);
    	ShoppingCart shoppingCart = findByPrincipal();
    	shoppingCart.getItems().remove(item);
        shoppingCartDAO.save(shoppingCart);

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
