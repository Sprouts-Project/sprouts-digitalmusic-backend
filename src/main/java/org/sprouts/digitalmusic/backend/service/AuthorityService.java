package org.sprouts.digitalmusic.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.sprouts.digitalmusic.backend.security.UserDetailsService;
import org.sprouts.digitalmusic.model.Authority;


@Service("authorityService")
public class AuthorityService {

    // Managed Data Access Objects --------------------------------------------

    // Simple CRUD Methods ----------------------------------------------------

    // Auxiliary methods -----------------------------------------------------

    public Authority findByPrincipal() {
        UserDetails userDetails = UserDetailsService.getPrincipal();
        Assert.notNull(userDetails);
		List<Authority> authorities = new ArrayList(userDetails.getAuthorities());
        Authority res = null;
        if (authorities.size() > 0) {
            res = authorities.get(0);
        }
        return res;
    }

}