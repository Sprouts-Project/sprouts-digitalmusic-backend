package org.sprouts.digitalmusic.backend.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.sprouts.digitalmusic.backend.da.CustomerDAO;
import org.sprouts.digitalmusic.backend.security.UserDetailsService;
import org.sprouts.digitalmusic.forms.CustomerForm;
import org.sprouts.digitalmusic.model.Customer;
import org.sprouts.digitalmusic.model.ShoppingCart;
import org.sprouts.digitalmusic.model.UserAccount;

@Service
public class CustomerService {

    // Managed Data Access Objects --------------------------------------------

    @Autowired
    private CustomerDAO customerDAO;

    // Managed Services -------------------------------------------------------

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    // Simple CRUD Methods ----------------------------------------------------

    public int save(Customer customer) {
        UserAccount userAccount = customer.getUserAccount();
        UserAccount userAccountDB = userAccountService.save(userAccount);
        customer.setUserAccount(userAccountDB);
        Customer customerDB = customerDAO.save(customer);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomer(customerDB);
        shoppingCart.setItems(new ArrayList<>());
        shoppingCartService.save(shoppingCart);
        return 1;
    }

    public Customer findByUsername(String username) {
        Customer principal = customerDAO.findByUsername(username);
        return principal;
    }

    public Customer getCustomerInfo() {
        Customer principal = findByUsername(UserDetailsService.getPrincipal().getUsername());
        principal.getUserAccount().setPassword("");
        return principal;
    }

    public Integer edit(CustomerForm customerForm) {
        Customer principal = findByUsername(UserDetailsService.getPrincipal().getUsername());
        StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder();
        Assert.isTrue(standardPasswordEncoder.matches(customerForm.getOldPassword(),principal.getUserAccount().getPassword()));

        if (!principal.getName().equals(customerForm.getName()))
            principal.setName(customerForm.getName());
        if (!principal.getEmail().equals(customerForm.getEmail()))
            principal.setEmail(customerForm.getEmail());
        if (!principal.getBirthdate().equals(customerForm.getBirthdate()))
            principal.setBirthdate(customerForm.getBirthdate());
        if (!principal.getSex().equals(customerForm.getSex()))
            principal.setSex(customerForm.getSex());
        if (!principal.getState().equals(customerForm.getState()))
            principal.setState(customerForm.getState());
        if (!principal.getUserAccount().getUsername().equals(customerForm.getUsername()))
            principal.getUserAccount().setUsername(customerForm.getUsername());
        if (!customerForm.getPassword().isEmpty() && !standardPasswordEncoder.matches(customerForm.getPassword(), principal.getUserAccount().getPassword()))
            principal.getUserAccount().setPassword(standardPasswordEncoder.encode(customerForm.getPassword()));
        customerDAO.save(principal);
        return 1;
    }

}
