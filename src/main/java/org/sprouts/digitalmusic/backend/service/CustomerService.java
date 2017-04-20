package org.sprouts.digitalmusic.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sprouts.digitalmusic.backend.da.CustomerDAO;
import org.sprouts.digitalmusic.model.Customer;
import org.sprouts.digitalmusic.model.UserAccount;

@Service
public class CustomerService {

    // Managed Data Access Objects --------------------------------------------

    @Autowired
    private CustomerDAO customerDAO;

    // Managed Services -------------------------------------------------------

    @Autowired
    private UserAccountService userAccountService;

    // Simple CRUD Methods ----------------------------------------------------

    public int save(Customer customer) {
        UserAccount userAccount = customer.getUserAccount();
        UserAccount userAccountDB = userAccountService.save(userAccount);
        customer.setUserAccount(userAccountDB);
        customerDAO.save(customer);
        return 1;
    }
}
