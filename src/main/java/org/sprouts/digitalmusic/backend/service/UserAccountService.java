package org.sprouts.digitalmusic.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.sprouts.digitalmusic.backend.da.UserAccountDAO;
import org.sprouts.digitalmusic.model.Authority;
import org.sprouts.digitalmusic.model.UserAccount;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserAccountService {

    // Managed Data Access Objects --------------------------------------------

    @Autowired
    private UserAccountDAO userAccountDAO;

    // Managed services -------------------------------------------------------

    @Autowired
    private AuthorityService authorityService;

    // Simple CRUD Methods ----------------------------------------------------

    public UserAccount save(UserAccount userAccount) {
        StandardPasswordEncoder encoder = new StandardPasswordEncoder();
        String hashedPassword = encoder.encode(userAccount.getPassword());
        userAccount.setPassword(hashedPassword);

        Set<Authority> authorities = new HashSet<>();
        Authority userAuthority = authorityService.findUserAuthority();
        authorities.add(userAuthority);
        userAccount.setAuthorities(authorities);
        
        UserAccount res = userAccountDAO.save(userAccount);
        return res;
    }
}
