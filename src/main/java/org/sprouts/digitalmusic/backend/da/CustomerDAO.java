package org.sprouts.digitalmusic.backend.da;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.sprouts.digitalmusic.model.Customer;

@Transactional
public interface CustomerDAO extends CrudRepository<Customer, Integer> {

    @Query("select c from Customer c where c.userAccount.username=?1")
    Customer findByUsername(String username);

}
