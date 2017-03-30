package org.sprouts.digitalmusic.backend.da;

import org.springframework.data.repository.CrudRepository;
import org.sprouts.digitalmusic.model.UserAccount;

public interface UserAccountDAO extends CrudRepository<UserAccount, Integer> {

    UserAccount findByUsername(String username);

}
