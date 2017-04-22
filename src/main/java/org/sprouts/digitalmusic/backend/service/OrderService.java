package org.sprouts.digitalmusic.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.sprouts.digitalmusic.backend.da.OrderDAO;
import org.sprouts.digitalmusic.backend.security.UserDetailsService;
import org.sprouts.digitalmusic.model.Customer;
import org.sprouts.digitalmusic.model.Order;
import org.sprouts.digitalmusic.model.OrderedItem;
import org.sprouts.digitalmusic.model.ShoppingCart;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    // Managed Data Access Objects --------------------------------------------

    @Autowired
    private OrderDAO orderDAO;

    // Managed Services -------------------------------------------------------

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private OrderedItemService orderedItemService;

    // Simple CRUD Methods ----------------------------------------------------

    public Integer save(Order order) {
        Customer customer = customerService.findByUsername(UserDetailsService.getPrincipal().getUsername());
        ShoppingCart shoppingCart = shoppingCartService.findByPrincipal();
        Assert.isTrue(shoppingCart.getItems().size() > 0);
        order.setCustomer(customer);
        order.setDate(new Date());
        order.setDeliveredDate(null);

        List<OrderedItem> orderedItems = new ArrayList<>();
        shoppingCart.getItems().forEach(item -> {
            OrderedItem orderedItem = new OrderedItem();
            if (orderedItems.stream().anyMatch(orderedItem1 -> orderedItem1.getItem().equals(item))) {
                orderedItems.get(orderedItems.indexOf(orderedItem)).setQuantity(orderedItems.get(orderedItems.indexOf(orderedItem)).getQuantity() + 1);
            } else {
                orderedItem.setItem(item);
                orderedItem.setOrder(order);
                orderedItem.setPrice(item.getPrice());
                orderedItem.setQuantity(1);
                orderedItems.add(orderedItem);
            }
        });
        order.setTotalPrice(orderedItems.stream().mapToDouble(orderedItem -> orderedItem.getQuantity() * orderedItem.getPrice()).sum());

        orderDAO.save(order);
        orderedItemService.saveAll(orderedItems);
        shoppingCartService.clear();
        return 1;
    }

}
