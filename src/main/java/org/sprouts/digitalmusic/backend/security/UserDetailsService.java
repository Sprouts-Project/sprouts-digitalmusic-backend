package org.sprouts.digitalmusic.backend.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.sprouts.digitalmusic.backend.da.UserAccountDAO;
import org.sprouts.digitalmusic.model.UserAccount;


@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {


    @Autowired
    private UserAccountDAO userAccountDAO;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {

        Assert.notNull(username);

        UserDetails result;

        result = userAccountDAO.findByUsername(username);
        Assert.notNull(result);
        // WARNING: The following sentences prevent lazy initialisation problems!
        Assert.notNull(result.getAuthorities());
        result.getAuthorities().size();

        return result;

    }

    public static UserDetails getPrincipal() {
        UserAccount result;
        SecurityContext context;
        Authentication authentication;
        Object principal;

        context = SecurityContextHolder.getContext();
        Assert.notNull(context);
        authentication = context.getAuthentication();
        Assert.notNull(authentication);
        principal = authentication.getPrincipal();

        result = (UserAccount) principal;
        Assert.notNull(result);

        return result;
    }

}
